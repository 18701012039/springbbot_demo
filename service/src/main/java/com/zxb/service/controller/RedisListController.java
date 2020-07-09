package com.zxb.service.controller;

import com.zxb.api.IRedisService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * redis List中的数据使用
 * @author zxb
 * @create 2020/7/9
 * @since 1.0.0
 */
@RestController
@RequestMapping("/list")
public class RedisListController {

    @Resource
    private IRedisService redisService;


    @RequestMapping(value = "/addListByRedis",name = "在redis添加一个集合",method = {RequestMethod.GET,RequestMethod.POST})
    public List<String> addListByRedis(){
        return redisService.addListByRedis();
    }

    @RequestMapping(value = "/listBasicMethod",name = "redis list一些操作",method = {RequestMethod.GET,RequestMethod.POST})
    public boolean listBasicMethod(){
        return redisService.listBasicMethod();
    }

    @RequestMapping(value = "/mapBasicMethod",name = "redis hash一些操作",method = {RequestMethod.GET,RequestMethod.POST})
    public boolean mapBasicMethod(){
        redisService.mapBasicMethod();
        return true;
    }

}
