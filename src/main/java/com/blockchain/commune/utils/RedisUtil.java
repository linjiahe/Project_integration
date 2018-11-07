package com.blockchain.commune.utils;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * redicache 工具类
 *
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    private static RedisUtil redisUtil;

    @PostConstruct
    private void init(){
        redisUtil = this;
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        redisUtil.redisTemplate = redisTemplate;
    }


    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }
    /**
     * 批量删除key
     *
     * @param pattern
     */
    public static void removePattern(final String pattern) {
        Set<Serializable> keys = redisUtil.redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisUtil.redisTemplate.delete(keys);
    }
    /**
     * 删除对应的value
     *
     * @param key
     */
    public static void remove(final String key) {
        if (exists(key)) {
            redisUtil.redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public static boolean exists(final String key) {
        return redisUtil.redisTemplate.hasKey(key);
    }
    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public static String get(final String key) {
        Object result = null;
        redisUtil.redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations<Serializable, Object> operations = redisUtil.redisTemplate.opsForValue();
        result = operations.get(key);
        if(result==null){
            return null;
        }
        return result.toString();
    }
    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisUtil.redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisUtil.redisTemplate.opsForValue();
            operations.set(key, value);
            redisUtil.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean hmset(String key, Map<String, String> value) {
        boolean result = false;
        try {
            redisUtil.redisTemplate.opsForHash().putAll(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String,String> hmget(String key) {
        Map<String,String> result =null;
        try {
            result=  redisUtil.redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用于限制时间内进行某次操作 （用于对用户频繁操作进行判断或者设置用户一天内进行操作次数等……）
     * @param key 键值
     * @param expireTime 失效时间
     * @param number 限制次数
     * @return 返回redis中key值的次数
     */
    public static long inscrement(String key, Long expireTime,long number) {
        long count = 0L;
        try {
            ValueOperations<Serializable, Object> operations = redisUtil.redisTemplate.opsForValue();
            count = operations.increment(key, 1);
            if (count > number) {
                return count;
            }
            redisUtil.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static Set<String> keys(String pattern) {
        try {
            Set<String> keys = redisUtil.redisTemplate.keys(pattern);
            return keys;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
