package com.zxb.service.service;

import com.zxb.api.IUserService;
import com.zxb.domain.User;
import com.zxb.init.SyncExecutor;
import com.zxb.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户接口实现类
 * @author zxb
 * @create 2020/6/15
 * @since 1.0.0
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private SyncExecutor syncExecutor;

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
    @Override
    public String syncExecutor(){
        List<User> syncNum=new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            User user=new User();
            user.setPassword("12345"+i);
            user.setUsername("zxb"+i);
            syncNum.add(user);
        }
        sync(syncNum);
        return "处理成功";
    }
    //用线程池来处理处理  比如你有1000万条数据那就需要把数据拆成很多段每一段用一个线程进行处理
    public void sync(List<User> syncNum){
        syncExecutor.getExecutorService().submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行的线程是"+Thread.currentThread().getId());
                systemNum(syncNum);
            }
        });
    }
    //需要被执行的任务
    public void systemNum(List<User> numList){
        try {
            userMapper.insertList(numList);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        System.out.println(Thread.currentThread().getId());
    }

    
}
