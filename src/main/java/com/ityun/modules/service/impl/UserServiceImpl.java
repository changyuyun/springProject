package com.ityun.modules.service.impl;

import com.ityun.modules.entity.User;
import com.ityun.modules.mapper.UserMapper;
import com.ityun.modules.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsernameAndPassword(username, password);
        return user;
    }
}
