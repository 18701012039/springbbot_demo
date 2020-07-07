package com.zxb.service.controller;

import com.zxb.api.IUserService;
import com.zxb.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
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
}
