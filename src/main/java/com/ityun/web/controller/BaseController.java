package com.ityun.web.controller;

import com.google.gson.Gson;
import com.ityun.base.lang.Consts;
import com.ityun.base.lang.Result;
import com.ityun.base.utils.RedisUtils;
import com.ityun.base.utils.TokenUtils;
import com.ityun.config.SiteConfig;
import com.ityun.modules.entity.User;
import com.ityun.modules.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    private String redisTokenUserKey = "User:token:%s";
    private String redisIdUserKey = "User:id:%s";
    @Autowired
    private UserService userService;

    @Autowired
    protected SiteConfig siteConfig;//网站配置

    private Long expiredTime = 3600L;

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    protected Result executeLogin(String username, String password) {
        Map<String, String> result = new HashMap<>();
        User user = userService.login(username, md5(password));
        if (user == null) {
            return Result.failure("username or password is error");
        }
        String token = TokenUtils.makeToken();
        result.put("id", String.valueOf(user.getId()));
        result.put("username", user.getUsername());
        result.put("name", user.getName());
        result.put("email", user.getEmail());
        result.put("avatar", user.getAvatar());
        result.put("gender", String.valueOf(user.getGender()));
        result.put("last_login", String.valueOf(user.getLast_login()));
        result.put("token", token);
        result.put("expiredTime", String.valueOf(expiredTime));
        tokenStore(token, result, expiredTime);
        userIdStore(String.valueOf(user.getId()), result);
        return Result.success("ok", getTokenInfoMap(token));
    }

    /**
     * 注册
     * @param user
     * @return
     */
    protected Result executeRegister(User user) {
        Date date = new Date();
        int ret = userService.register(user.getUsername(), user.getName(), user.getAvatar(), user.getEmail(), md5(user.getPassword()), user.getStatus(), date, user.getGender(), user.getComments(), user.getPost(), user.getSignature());
        if (ret <=0) {
            return Result.failure("error");
        }
        return Result.successMessage("ok");
    }

    /**
     * 登出
     * @param token
     * @return
     */
    protected Result executeLogout(String token) {
        Boolean ret = removeToken(token);
        if (!ret) {
            return Result.failure("error");
        }
        return Result.successMessage("ok");
    }

    protected Result executeEdit(User user, String token) {
        Map<String, String> tokenInfoMap = getTokenInfoMap(token);
        if (tokenInfoMap.isEmpty()) {
            return Result.failure(-2, "not login status");
        }
        //由token取出用户id
        int id = 0;
        for (Map.Entry<String, String> stringStringEntry : tokenInfoMap.entrySet()) {
            if ("id".equals(stringStringEntry.getKey())) {
                id = Integer.parseInt(stringStringEntry.getValue());
                break;
            }
        }
        int ret = userService.edit(id, user.getAvatar(), user.getSignature(), user.getName(), user.getEmail(), user.getGender());
        if (ret <=0) {
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

    protected boolean userIdStore(String id, Object data) {
        return RedisUtils.set(createRedisUserIdKey(id), data);
    }

    protected boolean removeToken(String token) {
        return RedisUtils.del(createRedisUserTokenKey(token));
    }

    protected String createRedisUserTokenKey(String token) {
        return String.format(redisTokenUserKey, token);
    }

    protected String createRedisUserIdKey(String id) {
        return String.format(redisIdUserKey, id);
    }

    protected Object getTokenInfo(String token) {
        return getTokenInfoMap(token);
    }

    protected Map<String, String> getTokenInfoMap(String token) {
        Map<String, String> result = new HashMap<>();
        Object info = RedisUtils.get(createRedisUserTokenKey(token));
        if (info == null) {
            return result;
        }
        Gson gson = new Gson();
        String json = gson.toJson(info);
        result = gson.fromJson(json, Map.class);
        for (Map.Entry<String, String> resultEntry : result.entrySet()) {
            if ("avatar".equals(resultEntry.getKey())) {
                if (resultEntry.getValue().length() == 0) {
                    result.replace("avatar", Consts.AVATAR);
                } else {
                    result.replace("avatar", siteConfig.getUrl() + resultEntry.getValue());
                }
            }
        }
        return result;
    }

}
