package com.ityun.modules.service.impl;

import com.ityun.modules.entity.User;
import com.ityun.modules.mapper.UserMapper;
import com.ityun.modules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsernameAndPassword(username, password);
        return user;
    }

    @Override
    public int register(String username, String name, String avatar, String email, String password, int status, Date created, int gender, int comments, int posts, String signature) {
        return userMapper.addUser(username, name, avatar, email, password, status, created, gender, comments, posts, signature);
    }

    @Override
    public int edit(int id, String avatar, String signature, String name, String email, int gender) {
        return userMapper.updateUser(id, avatar, signature, name, email, gender);
    }
}
