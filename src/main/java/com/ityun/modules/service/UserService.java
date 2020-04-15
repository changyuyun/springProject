package com.ityun.modules.service;

import com.ityun.modules.entity.User;

public interface UserService {
    User login(String username, String password);
}
