package com.ityun.base.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;

/**
 * redisTemplate封装
 *
 *  @author yinxp@dist.com.cn
 */
@Component
public class RedisUtils {
    static RedisTemplate<Object, Object> JsonRedisTemplate;

    public RedisUtils(RedisTemplate<Object, Object> redisTemplate) {
        this.JsonRedisTemplate = redisTemplate;
    }

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return boolean
     */
    public static boolean expire(String key,long time){
        try {
            if(time>0){
                JsonRedisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static long getExpire(String key){
        return JsonRedisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(String key){
        try {
            return JsonRedisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public static boolean del(String ... key){
        if(key!=null&&key.length>0){
            if(key.length==1){
                return JsonRedisTemplate.delete(key[0]);
            }else{
                JsonRedisTemplate.delete(CollectionUtils.arrayToList(key));
                return true;
            }
        } else {
            return false;
        }
    }

    //============================String=============================
    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public static Object get(String key){
        return key==null?null:JsonRedisTemplate.opsForValue().get(key);
    }
    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean set(String key,Object value) {
        try {
            JsonRedisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static boolean set(String key,Object value,long time){
        try {
            if(time>0){
                JsonRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}