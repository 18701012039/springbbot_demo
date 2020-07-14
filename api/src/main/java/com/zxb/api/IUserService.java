package com.zxb.api;

import com.zxb.domain.User;

import java.util.List;

/**
 * 操作user的所有接口
 * @author zxb
 * @date 2020/06/15
 */
public interface IUserService {

    /**
     * 查询所有的user集合
     * @return 返回的user集合
     */
    List<User> queryUserList();

    /**
     * 根据id查询一条数据
     * @param id 用户id
     * @return 查询一条数据
     */
    User queryUserById(Long id);

    List<User> queryAll() throws Exception;

    boolean addUser();

    String syncExecutor();

    List<User> redisCache(String name);

    void deleteCache(String name,Long id);


}
