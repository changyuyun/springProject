package com.ityun.web.controller;

import com.google.gson.Gson;
import com.ityun.base.lang.Result;
import com.ityun.base.utils.RedisUtils;
import com.ityun.base.utils.TokenUtils;
import com.ityun.modules.entity.User;
import com.ityun.modules.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    private String redisTokenUserKey = "User:token:%s";
    @Autowired
    private UserService userService;

    private Long expiredTime = 3600L;

    protected Result executeLogin(String username, String password) {
        Map<String, String> result = new HashMap<>();
        User user = userService.login(username, md5(password));
        if (user == null) {
            return Result.failure("username or password is error");
        }
        String token = TokenUtils.makeToken();
        result.put("username", user.getUsername());
        result.put("name", user.getName());
        result.put("email", user.getEmail());
        result.put("avatar", user.getAvatar());
        result.put("gender", String.valueOf(user.getGender()));
        result.put("last_login", String.valueOf(user.getLast_login()));
        result.put("token", token);
        result.put("expiredTime", String.valueOf(expiredTime));
        tokenStore(token, result, expiredTime);
        return Result.success("ok", result);
    }

    protected Result executeRegister(User user) {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        Date date = new Date();
        int ret = userService.register(user.getUsername(), user.getName(), user.getAvatar(), user.getEmail(), md5(user.getPassword()), user.getStatus(), date, user.getGender(), user.getComments(), user.getPost(), user.getSignature());
        if (ret <=0) {
            return Result.failure("error");
        }
        return Result.successMessage("ok");
    }

    protected Result executeLogout(String token) {
        Boolean ret = removeToken(token);
        if (!ret) {
            return Result.failure("error");
        }
        return Result.successMessage("ok");
    }

    protected String md5(String needle) {
        return DigestUtils.md5Hex(needle).toUpperCase();
    }

    protected boolean tokenStore(String token, Object data, Long expiredTime) {
        return RedisUtils.set(createRedisUserTokenKey(token), data, expiredTime);
    }

    protected boolean removeToken(String token) {
        return RedisUtils.del(createRedisUserTokenKey(token));
    }

    protected String createRedisUserTokenKey(String token) {
        return String.format(redisTokenUserKey, token);
    }

    protected Object getTokenInfo(String token) {
        return RedisUtils.get(createRedisUserTokenKey(token));
    }

}
