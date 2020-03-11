package com.example.shirodemo.dao;

import com.example.shirodemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    List<User> getAllUser();
    int addUser(User user);
    int updateUser(User user);
    int delUserById(@Param("userphone") String userphone);
    User findByPhone(@Param("userphone") String userphone);
}
