package com.zxb.service.controller;

import com.zxb.api.IUserService;
import com.zxb.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 用mybatis中的xml来调用数据库
 * @author zxb
 * @create 2020/6/15
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/queryUserList",name = "查询所有对象",method={RequestMethod.POST,RequestMethod.GET})
    public List<User> queryUserList(){
        return userService.queryUserList();
    }

    @RequestMapping(value = "/queryByUserId",name = "根据id获取一个对象",method = RequestMethod.GET)
    public User queryByUserId(Long id){
        return userService.queryUserById(id);
    }

    @RequestMapping(value = "/queryAll",name = "用mybatis原有的底层执行sql",method ={ RequestMethod.GET,RequestMethod.POST})
    public List<User> queryAll(){
        try {
            return userService.queryAll();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return new ArrayList<>();
        }
    }
    @RequestMapping(value = "/addUser",name = "新增一条数据",method ={ RequestMethod.GET,RequestMethod.POST})
    public boolean addUser(){
        return userService.addUser();
    }

    @RequestMapping(value = "/syncExecutor",name = "用多线程来处理数据",method ={ RequestMethod.GET,RequestMethod.POST})
    public String syncExecutor(){
        return userService.syncExecutor();
    }

    @RequestMapping(value = "/redisCache",name = "用注解的方式将mysql中的数据缓存在redis中",method ={ RequestMethod.GET,RequestMethod.POST})
    public List<User> redisCache(@RequestParam(name = "name", required = false)  String name){
        if (StringUtils.isEmpty(name)) {
            throw new HttpMessageNotReadableException("入参不能为空");
        }

       return userService.redisCache(name);
    }
    @RequestMapping(value = "/deleteCache",name = "删除缓存",method ={ RequestMethod.GET,RequestMethod.POST})
    public boolean deleteCache(String name,Long id){
        userService.deleteCache(name,id);
        return true;
    }
}
