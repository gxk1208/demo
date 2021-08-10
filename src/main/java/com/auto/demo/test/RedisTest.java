package com.auto.demo.test;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/8/6 14:25
 */

public class RedisTest {

    private static RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

    public static void main(String[] args) {
        redisTemplate.opsForValue().set("测试","测试");
    }
}
