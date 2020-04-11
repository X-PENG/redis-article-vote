package com.peng.redistest3;

import com.alibaba.fastjson.JSON;
import com.peng.redistest3.dao.TagDao;
import com.peng.redistest3.utils.KeyDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest3ApplicationTests {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    TagDao tagDao;

    @Test
    public void t1(){
        long id=1;
        String userJson=stringRedisTemplate.opsForValue().get(KeyDefinition.KEY_FOR_USER_PREFIX+id);
        System.out.println(userJson==null);
        System.out.println("----");
        JSON.parseObject(userJson);
    }

    @Test
    public void t2(){
        System.out.println(tagDao.getAllBasicInfo()==null);
        System.out.println(tagDao.getAllBasicInfo().size());
    }
    @Test
    public void t3(){
        long id=1L;
        Map<Object, Object> map=stringRedisTemplate.opsForHash().entries(KeyDefinition.HASH_KEY_FOR_ARTICLE_PREFIX+id);
        System.out.println(JSON.toJSONString(map));
    }
}
