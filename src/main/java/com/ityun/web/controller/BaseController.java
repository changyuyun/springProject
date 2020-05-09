package com.ityun.web.controller;

import com.google.gson.Gson;
import com.ityun.base.lang.Consts;
import com.ityun.base.lang.Result;
import com.ityun.base.lang.ResultConst;
import com.ityun.base.utils.RedisUtils;
import com.ityun.base.utils.TokenUtils;
import com.ityun.config.SiteConfig;
import com.ityun.modules.entity.Pager;
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
            return Result.failure(ResultConst.userCode.LOGIN_FAILURE, ResultConst.userMESSAGE.LOGIN_FAILURE);
        }

        String token = TokenUtils.makeToken();
        result.put("id", String.valueOf(user.getId()));
        result.put("username", user.getUsername());
        result.put("name", user.getName());
        result.put("email", user.getEmail());
        result.put("avatar", user.getAvatar());
        result.put("gender", String.valueOf(user.getGender()));
        result.put("comments", String.valueOf(user.getComments()));
        result.put("posts", String.valueOf(user.getPosts()));
        result.put("signature", user.getSignature());
        result.put("last_login", String.valueOf(user.getLast_login()));
        result.put("token", token);
        result.put("expiredTime", getExpiredTime());
        tokenStore(token, result, expiredTime);
        userIdStore(String.valueOf(user.getId()), result);
        return Result.success(ResultConst.commonMessage.COMMON_SUCCESS, getTokenInfoMap(token));
    }

    /**
     * 注册
     * @param user
     * @return
     */
    protected Result executeRegister(User user) {
        User checkRet = userService.checkUsername(user.getUsername());
        if (checkRet != null) {
            return Result.failure(ResultConst.userCode.USERNAME_EXIT, ResultConst.userMESSAGE.USERNAME_EXIT);
        }
        Date date = new Date();
        int ret = userService.register(user.getUsername(), user.getName(), user.getAvatar(), user.getEmail(), md5(user.getPassword()), user.getStatus(), date, user.getGender(), user.getComments(), user.getPosts(), user.getSignature());
        if (ret <=0) {
            return Result.failure(ResultConst.commonMessage.COMMON_FAILURE);
        }
        return Result.successMessage(ResultConst.commonMessage.COMMON_SUCCESS);
    }

    /**
     * 登出
     * @param token
     * @return
     */
    protected Result executeLogout(String token) {
        Boolean ret = removeToken(token);
        if (!ret) {
            return Result.failure(ResultConst.commonMessage.COMMON_FAILURE);
        }
        return Result.successMessage(ResultConst.commonMessage.COMMON_FAILURE);
    }

    protected Result executeEdit(User user, String token) {
        int id = token2Id(token);
        if (id == 0) {
            return Result.failure(ResultConst.commonCode.COMMON_AUTH_FAILURE, ResultConst.commonMessage.COMMON_AUTH_FAILURE);
        }
        int ret = userService.edit(id, user.getAvatar(), user.getSignature(), user.getName(), user.getEmail(), user.getGender());
        if (ret <=0) {
            return Result.failure(ResultConst.commonMessage.COMMON_FAILURE);
        }
        return Result.successMessage(ResultConst.commonMessage.COMMON_FAILURE);
    }

    protected Result executePasswordReset(User user) {
        int id = token2Id(user.getToken());
        if (id == 0) {
            return Result.failure(ResultConst.commonCode.COMMON_AUTH_FAILURE, ResultConst.commonMessage.COMMON_AUTH_FAILURE);
        }
        int ret = userService.passwordReset(id, md5(user.getPassword()), md5(user.getNewPassword()));
        if (ret < 0) {
            return Result.failure(ResultConst.userCode.OLD_PASSWORD_ERROR, ResultConst.userMESSAGE.OLD_PASSWORD_ERROR);
        } else if (ret == 0) {
            return Result.failure(ResultConst.commonMessage.COMMON_FAILURE);
        } else {
            return Result.successMessage(ResultConst.commonMessage.COMMON_FAILURE);
        }
    }

    protected String md5(String needle) {
        return DigestUtils.md5Hex(needle).toUpperCase();
    }

    /**
     * 构造过期时间
     * @return
     */
    protected String getExpiredTime() {
        long nowTime = System.currentTimeMillis();
        return String.valueOf(nowTime + expiredTime*1000);
    }

    /**
     * 刷新token过期时间
     * @param token
     * @return
     */
    protected boolean refreshToken(String token) {
        Map<String, String> userInfo = getTokenInfoMap(token);
        if (userInfo == null) {
            return false;
        }
        userInfo.replace("expiredTime", getExpiredTime());
        return tokenStore(token, userInfo, expiredTime);
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

    protected int token2Id(String token) {
        int id = 0;
        Map<String, String> tokenInfoMap = getTokenInfoMap(token);
        if (!tokenInfoMap.isEmpty()) {
            //由token取出用户id
            for (Map.Entry<String, String> stringStringEntry : tokenInfoMap.entrySet()) {
                if ("id".equals(stringStringEntry.getKey())) {
                    id = Integer.parseInt(stringStringEntry.getValue());
                    break;
                }
            }
        }
        return id;
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

    protected Pager getPager(int currentPage, int totalRecord, int totalPage, Object data) {
        Pager pager = new Pager();
        pager.setCurrentPage(currentPage);
        pager.setTotalRecord(totalRecord);
        pager.setTotalPage(totalPage);
        pager.setData(data);
        return pager;
    }

}
