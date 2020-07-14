package com.zxb.service.mapper;

import com.zxb.domain.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import java.util.List;

public interface UserMapper extends Mapper<User>, MySqlMapper<User> {
    List<User> queryByUserList();

    List<User> queryUserList();

    List<User> queryByUserName(@Param("userName")String userName);

    void deleteById(@Param("id")Long id);
}