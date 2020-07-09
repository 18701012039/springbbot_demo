package com.zxb.service.controller;

import com.zxb.api.IRedisService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * redis测试 字符串操作
 * @author zxb
 * @create 2020/7/8
 * @since 1.0.0
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private IRedisService redisService;
    @RequestMapping(value = "/addRedisString",name = "新增一条redis数据",method = {RequestMethod.GET,RequestMethod.POST})
    public String addRedisString(String redisString){
        redisService.addRedisString(redisString);
        return "redisString";
    }

    @RequestMapping(value = "/deleteRedisString",name="删除一条redis数据",method = {RequestMethod.GET,RequestMethod.POST})
    public String deleteRedisString(String key){
        return redisService.deleteRedisString(key);
    }

    @RequestMapping(value = "/getString",name="根据key获取一条数据",method = {RequestMethod.GET,RequestMethod.POST})
    public String getString(String key){
        return redisService.getString(key);
    }
    @RequestMapping(value = "/addSet",method = {RequestMethod.GET,RequestMethod.POST})
    public boolean addSet(String valueOne,String valueTwo){
        return true;
    }

    @RequestMapping(value = "/getKeyFlag",name="判断key是否存在可以用这个命令实现分布式锁",method = {RequestMethod.GET,RequestMethod.POST})
    boolean getKeyFlag(String key) throws Exception{
        redisService.getKeyFlag(key);
        return true;
    }
    @RequestMapping(value = "/addMapKey",name="用map存储存储多条数据",method = {RequestMethod.GET,RequestMethod.POST})
    boolean addMapKey(){
        redisService.addMapKey();
        return true;
    }
    @RequestMapping(value = "/getMapKey",name="根据多个key获取多条数据",method = {RequestMethod.GET,RequestMethod.POST})
    List<String> getMapKey(){
        return redisService.getMapKey();
    }
    @RequestMapping(value = "/getAndSet",name="更新值返回旧的值",method = {RequestMethod.GET,RequestMethod.POST})
    String getAndSet(){
        return redisService.getAndSet();
    }
    @RequestMapping(value = "/getIncrement",name="将原有的值和现在的值相加可以用在点赞",method = {RequestMethod.GET,RequestMethod.POST})
    Long getIncrement(){
        return redisService.getIncrement();
    }
    @RequestMapping(value = "/redisStringAppend",name="字符串追加",method = {RequestMethod.GET,RequestMethod.POST})
    String redisStringAppend(){
        return redisService.redisStringAppend();
    }
}
