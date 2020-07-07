package com.zxb.service.service;

import com.zxb.api.IUserService;
import com.zxb.domain.User;
import com.zxb.service.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户接口实现类
 * @author zxb
 * @create 2020/6/15
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> queryUserList() {
        return userMapper.queryByUserList();
    }

    @Override
    public User queryUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addUser() {
        User user=new User();
        user.setId(16L);
        user.setUsername("zxn");
        user.setPassword("123456");
        userMapper.insert(user);
        //抛出异常是否回滚
        try {
            int i=1/0;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    @Override
    @Transactional
    public List<User> queryAll() throws Exception {
        return userMapper.queryByUserList();
    }
}
