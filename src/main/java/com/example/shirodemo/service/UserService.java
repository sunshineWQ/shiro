package com.example.shirodemo.service;

import com.example.shirodemo.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser();
    int addUser(User user);
    int updateUser(User user);
    int delUserById(String userphone);
    User findByPhone(String userphone);
}
