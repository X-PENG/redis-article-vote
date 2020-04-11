package com.peng.redistest3.dao.imp;

import com.alibaba.fastjson.JSON;
import com.peng.redistest3.dao.UserDao;
import com.peng.redistest3.utils.KeyDefinition;
import com.peng.redistest3.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImp implements UserDao {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public UserVO addUser(UserVO userVO) {
        long id= stringRedisTemplate.opsForValue().increment(KeyDefinition.ID_GENERATOR_FOR_USER, 1L);
        userVO.setId(id);
        stringRedisTemplate.opsForValue().set(KeyDefinition.KEY_FOR_USER_PREFIX+id, JSON.toJSONString(userVO));
        return userVO;
    }

    @Override
    public UserVO getOne(long id) {
        String userJson=stringRedisTemplate.opsForValue().get(KeyDefinition.KEY_FOR_USER_PREFIX+id);
        return JSON.parseObject(userJson,UserVO.class);
    }

    @Override
    public List<UserVO> getAll() {
        Set<String> keys=stringRedisTemplate.keys(KeyDefinition.KEY_FOR_USER_PREFIX+"*");
        List<UserVO> list=new ArrayList<>();
        for (String key:keys){
            list.add(JSON.parseObject(stringRedisTemplate.opsForValue().get(key),UserVO.class));
        }
        return list;
    }
}
