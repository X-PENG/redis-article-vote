package com.peng.redistest3.dao.imp;

import com.alibaba.fastjson.JSON;
import com.peng.redistest3.dao.TagDao;
import com.peng.redistest3.utils.KeyDefinition;
import com.peng.redistest3.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class TagDaoImp implements TagDao {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public TagVO addTag(TagVO tag) {
        long id=stringRedisTemplate.opsForValue().increment(KeyDefinition.ID_GENERATOR_FOR_TAG,1L);
        tag.setId(id);
        stringRedisTemplate.opsForValue().set(KeyDefinition.KEY_FOR_TAG_PREFIX+id, JSON.toJSONString(tag));
        return tag;
    }

    @Override
    public TagVO getOneBasicInfo(long id) {
        String tagJson=stringRedisTemplate.opsForValue().get(KeyDefinition.KEY_FOR_TAG_PREFIX+id);
        return JSON.parseObject(tagJson,TagVO.class);
    }

    @Override
    public List<TagVO> getAllBasicInfo() {
        Set<String> keys=stringRedisTemplate.keys(KeyDefinition.KEY_FOR_TAG_PREFIX+"*");
        List<TagVO> tagVOS=new ArrayList<>();
        for(String key:keys){
            System.out.println(key);
            tagVOS.add(JSON.parseObject(stringRedisTemplate.opsForValue().get(key),TagVO.class));
        }
        return tagVOS;
    }
}
