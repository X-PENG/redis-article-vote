package com.peng.redistest3.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


//https://blog.csdn.net/jdd92/article/details/81053404
//在项目启动成功后，做一些初始化工作
@Component
public class DBInitializer implements ApplicationRunner {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("----------启动成功-----------------");
        if(!stringRedisTemplate.hasKey("articleIdSeq")){
            stringRedisTemplate.opsForValue().set("articleIdSeq","0");
        }
        if(!stringRedisTemplate.hasKey("tagIdSeq")){
            stringRedisTemplate.opsForValue().set("tagIdSeq","0");
        }
        if(!stringRedisTemplate.hasKey("userIdSeq")){
            stringRedisTemplate.opsForValue().set("userIdSeq","0");
        }
    }
}
