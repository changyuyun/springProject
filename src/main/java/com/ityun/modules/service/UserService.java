package com.ityun.modules.service;

import com.ityun.modules.entity.User;

import java.util.Date;

public interface UserService {
    User login(String username, String password);

    User checkUsername(String username);

    int register(String username, String name, String avatar, String email, String password, int status, Date created, int gender, int comments, int posts, String signature);

    int edit(int id, String avatar, String signature, String name, String email, int gender);

    int passwordReset(int id, String password, String newPassword);
}
