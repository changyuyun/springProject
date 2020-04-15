package com.ityun.base.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

/**
 * token工具类
 */
public class TokenUtils {

    public static String makeToken() {
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
        token = DigestUtils.md5Hex(token).toUpperCase();
        return token;
    }
}
