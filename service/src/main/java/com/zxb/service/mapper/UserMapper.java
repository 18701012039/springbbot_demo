package com.zxb.service.mapper;

import com.zxb.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> queryByUserList();

    List<User> queryUserList();

    @Select("SELECT * FROM user")
    List<User> queryAll();
}