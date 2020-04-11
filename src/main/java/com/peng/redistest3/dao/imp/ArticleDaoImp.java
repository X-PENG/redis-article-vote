package com.peng.redistest3.dao.imp;

import com.alibaba.fastjson.JSON;
import com.peng.redistest3.dao.ArticleDao;
import com.peng.redistest3.utils.KeyDefinition;
import com.peng.redistest3.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Repository
public class ArticleDaoImp implements ArticleDao {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public ArticleVO addArticle(ArticleVO articleVO) throws IllegalAccessException {
        long id=stringRedisTemplate.opsForValue().increment(KeyDefinition.ID_GENERATOR_FOR_ARTICLE,1L);
        articleVO.setId(id);
        Map<String, String> map=generateMap(articleVO);
        stringRedisTemplate.opsForHash().putAll(KeyDefinition.HASH_KEY_FOR_ARTICLE_PREFIX+id,map);
        //还要将该记录记录到两个zset中，用于排行
        stringRedisTemplate.opsForZSet().add(KeyDefinition.ZSET_KEY_FOR_ATICLES_SORTED_ON_TIME,id+"",articleVO.getCreateTime().getTime());
        stringRedisTemplate.opsForZSet().add(KeyDefinition.ZSET_KEY_FOR_ATICLES_SORTED_ON_VOTES,id+"",articleVO.getVotes());
        //还要将该文章id存储到所属标签中
        String[] tagIds=articleVO.getTags().split(",");
        for(String tagId:tagIds){
            stringRedisTemplate.opsForSet().add(KeyDefinition.SET_KEY_FOR_TAG_ATICLES_PREFIX+tagId,id+"");
        }
        //还要将该文章添加到用户下
        stringRedisTemplate.opsForSet().add(KeyDefinition.SET_KEY_FOR_USER_ATICLES_PREFIX+articleVO.getUserId(),id+"");
        return articleVO;
    }

    @Override
    public ArticleVO getOne(long id) {
        Map<Object, Object> map=stringRedisTemplate.opsForHash().entries(KeyDefinition.HASH_KEY_FOR_ARTICLE_PREFIX+id);
        return JSON.parseObject(JSON.toJSONString(map), ArticleVO.class);
    }

    @Override
    public ArticleVO voteOne(long userId, long articleId) {
        //先检查该用户是否投过票
        //该文章投票用户集合add当前用户id
        if(stringRedisTemplate.opsForSet().add(KeyDefinition.SET_KEY_FOR_ARTICLE_VOTED_USERS_PREFIX+articleId,userId+"")==0){
            //等于0，代表添加失败，集合中已存在
            System.out.println("用户["+userId+"]已经给文章["+articleId+"]投过票");
            return getOne(articleId);
        }
        //文章票数+1
        stringRedisTemplate.opsForHash().increment(KeyDefinition.HASH_KEY_FOR_ARTICLE_PREFIX+articleId,"votes",1L);
        //还要更新一个zset中的票数
        stringRedisTemplate.opsForZSet().incrementScore(KeyDefinition.ZSET_KEY_FOR_ATICLES_SORTED_ON_VOTES,articleId+"",1L);
        return getOne(articleId);//返回文章信息
    }

    @Override
    public List<ArticleVO> voteMulti(long userId, long[] articleIds) {
        List<ArticleVO> articleVOS=new ArrayList<>();
        for(long articleId:articleIds){
            articleVOS.add(voteOne(userId,articleId));
        }
        return articleVOS;
    }

    @Override
    public List<ArticleVO> getTopN(int n) {
        return findByPage(KeyDefinition.ZSET_KEY_FOR_ATICLES_SORTED_ON_VOTES,0,n-1,1);
    }

    @Override
    public List<ArticleVO> getLatestN(int n) {
        return findByPage(KeyDefinition.ZSET_KEY_FOR_ATICLES_SORTED_ON_TIME,0,n-1,1);
    }


    /**
     *
     * @param n
     * @param tagId
     * @return
     * 做法：保存该标签的所有文章id的set类型的key，与票数的zset做交集即可，ZINTERSTORE返回的也是zset
     */
    @Override
    public List<ArticleVO> getTopNInTag(int n, long tagId) {
        String destKey="tag:temp:votes:"+tagId;
        return getByTag(KeyDefinition.ZSET_KEY_FOR_ATICLES_SORTED_ON_VOTES,destKey,tagId,n);
    }

    @Override
    public List<ArticleVO> getLatestNInTag(int n, long tagId) {
        String destKey="tag:temp:time:"+tagId;
        return getByTag(KeyDefinition.ZSET_KEY_FOR_ATICLES_SORTED_ON_TIME,destKey,tagId,n);
    }

    @Override
    public List<ArticleVO> pageOnTime(int page, int pageSize, int direction) {
        int offset=(page-1)*pageSize;
        return findByPage(KeyDefinition.ZSET_KEY_FOR_ATICLES_SORTED_ON_TIME,offset,pageSize-1,direction);
    }

    @Override
    public List<ArticleVO> pageOnVotes(int page, int pageSize, int direction) {
        int offset=(page-1)*pageSize;
        return findByPage(KeyDefinition.ZSET_KEY_FOR_ATICLES_SORTED_ON_VOTES,offset,pageSize-1,direction);
    }

    //想要实现排序查询，就要使用zset。做交集即可，不用存储冗余数据。
    @Override
    public List<ArticleVO> getArticlesByUser(long userId) {
        String key1=KeyDefinition.SET_KEY_FOR_USER_ATICLES_PREFIX+userId;
        String key2=KeyDefinition.ZSET_KEY_FOR_ATICLES_SORTED_ON_TIME;
        String destKey="user:temp:time:"+userId;
        //destKey是zset类型，因为用的是zset操作
        stringRedisTemplate.opsForZSet().intersectAndStore(key1,key2,destKey);
        //做交集后，destKey中就是按顺序保存了 该用户所有文章id

        //此set不是HashSet，而是有序的
        Set<String> articleIds = stringRedisTemplate.opsForZSet().reverseRange(destKey, 0, -1);
        List<ArticleVO> list=new ArrayList<>();
        for(String id:articleIds){
            list.add(getOne(Long.parseLong(id)));
        }
        stringRedisTemplate.expire(destKey,30, TimeUnit.SECONDS);
        return list;
    }

    private Map<String, String> generateMap(ArticleVO articleVO) throws IllegalAccessException {
        Map<String, String> map=new HashMap<>();
        Class clazz=ArticleVO.class;
        Field[] fields=clazz.getDeclaredFields();
        for (Field field:fields){
            if(Modifier.isStatic(field.getModifiers())) continue;
            field.setAccessible(true);
            if (field.getName().equals("createTime")){
                map.put(field.getName(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)field.get(articleVO)));
                continue;
            }
            map.put(field.getName(),field.get(articleVO).toString());
        }
        return map;
    }

    /**
     *
     * @param zsetKey       表示按时间还是票数排序
     * @param offset        偏移指定位数查询
     * @param pageSize      页面大小
     * @param direction     0升序1降序
     * @return
     */
    private List<ArticleVO> findByPage(String zsetKey,int offset,int pageSize,int direction){
        Set<String> articleIds =null;
        if(direction==0){
            //返回的不是HashSet，HashSet是无序的。
            articleIds=stringRedisTemplate.opsForZSet().range(zsetKey, offset, offset+pageSize);
        }else{
            articleIds=stringRedisTemplate.opsForZSet().reverseRange(zsetKey, offset, offset+pageSize);
        }
        List<ArticleVO> articleVOS=new ArrayList<>();
        for(String id:articleIds){
            articleVOS.add(getOne(Long.parseLong(id)));
        }
        return articleVOS;
    }

    private List<ArticleVO> getByTag(String zsetKey,String destKey,long tagId,int n){
        String key1=KeyDefinition.SET_KEY_FOR_TAG_ATICLES_PREFIX+tagId;
        String key2=zsetKey;
        /**
         *
         * 对应zset的命令:ZINTERSTORE destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]
         * 默认情况下，结果集中某个成员的 score 值是所有给定集下该成员 score 值之和 。
         * 但是intersectAndStore方法不能设置weights，乘法因子默认设置为 1 。
         *
         * 注意：
         * 如果是将set key和zset key做交集，set key每个成员默认score为1，而不是0。
         */
        stringRedisTemplate.opsForZSet().intersectAndStore(key1,key2,destKey);
        //destKey必须每次都生成，设置过期，没必要长期存在
        stringRedisTemplate.expire(destKey,30, TimeUnit.SECONDS);
        return findByPage(destKey,0,n-1,1);
    }
}
