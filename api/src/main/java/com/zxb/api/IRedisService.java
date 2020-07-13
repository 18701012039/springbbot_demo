package com.zxb.api;

import java.util.List;

/**
 * 简单redis测试
 * @author zxb
 * @create 2020/7/8
 * @since 1.0.0
 */
public interface IRedisService {

    boolean addRedisString(String name);

    String deleteRedisString(String key);

    String getString(String key);

    boolean addSet(String valueOne,String valueTwo);

    boolean getKeyFlag(String key) throws Exception;

    boolean addMapKey();

    List<String> getMapKey();

    String getAndSet();

    Long getIncrement();

    String redisStringAppend();




    List<String> addListByRedis();

    boolean listBasicMethod();

    boolean mapBasicMethod();

    boolean setBasicMethod();

}
