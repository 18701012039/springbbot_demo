package com.zxb.service.service;

import com.zxb.anno.DataSourceAnn;
import com.zxb.api.IDataResourceService;
import com.zxb.domain.DataSourceNames;
import com.zxb.domain.User;
import com.zxb.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 切换数据源实现插入到不同的数据库中
 * @author admin
 * @create 2020/7/22
 * @since 1.0.0
 */
@Service
public class DataResourceServiceImpl implements IDataResourceService {

    @Resource
    private UserMapper userMapper;

    /**
     * 向数据库zxbOne添加数据
     */
    @DataSourceAnn(DataSourceNames.ONE)
    @Override
    public void saveUserOne() {
        User user=new User();
        user.setId(4L);
        user.setUserName("zxbOne");
        user.setPassword("123");
        userMapper.insert(user);
    }

    /**
     * 向zxbTwo添加数据
     */
    @DataSourceAnn(DataSourceNames.TWO)
    @Override
    public void saveUserTwo() {
        User user=new User();
        user.setId(4L);
        user.setUserName("zxbTwo");
        user.setPassword("456");
        userMapper.insert(user);
    }
}
