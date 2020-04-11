package com.peng.redistest3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisTest3Application {

    public static void main(String[] args) {
        SpringApplication.run(RedisTest3Application.class, args);
    }

}
