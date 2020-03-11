package com.example.shirodemo.service.impl;

import com.example.shirodemo.dao.UserDao;
import com.example.shirodemo.entity.User;
import com.example.shirodemo.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    public int addUser(User user) {
        String userid= UUID.randomUUID().toString().substring(0,6)+ new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String usersalt=UUID.randomUUID().toString();
        Md5Hash md5Hash =new Md5Hash(user.getPassword(),usersalt,1000);
        //秘文采用base64格式化后存入对象
        user.setPassword(md5Hash.toBase64());
        user.setUserid(userid);
        user.setUsersalt(usersalt);
        return userDao.addUser(user);
    }

    @Override
    public int updateUser(User user) {
        String usersalt=UUID.randomUUID().toString();
        Md5Hash md5Hash =new Md5Hash(user.getPassword(),usersalt,1000);
        user.setPassword(md5Hash.toBase64());
        user.setUsersalt(usersalt);
        return userDao.updateUser(user);
    }

    @Override
    public int delUserById(String userphone) {
        return userDao.delUserById(userphone);
    }

    @Override
    public User findByPhone(String userphone) {
        return userDao.findByPhone(userphone);
    }
}
