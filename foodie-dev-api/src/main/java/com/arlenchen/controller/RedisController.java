package com.arlenchen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author arlenchen
 */
@RestController
public class RedisController {
    private final RedisTemplate redisTemplate;
    @Autowired
    public RedisController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/get")
    public  Object get(String key){

        return  redisTemplate.opsForValue().get(key);
    }
    @GetMapping("/set")
    public  Object set(String key ,String value){
        redisTemplate.opsForValue().set(key,value);
        return  "OK";
    }
    @GetMapping("/delete")
    public  Object delete(String key ){
        redisTemplate.delete(key);
        return  "OK";
    }
}
