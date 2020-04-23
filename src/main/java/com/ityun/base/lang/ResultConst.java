package com.ityun.base.lang;

public interface ResultConst {
    interface commonCode {
        int COMMON_FAILURE = -1;
        int COMMON_SUCCESS = 0;
        int COMMON_AUTH_FAILURE = -2;
        int COMMON_SIGN_ERROR = -3;
    }

    interface commonMessage {
        String COMMON_FAILURE = "失败";
        String COMMON_SUCCESS = "成功";
        String COMMON_AUTH_FAILURE = "未登录";
        String COMMON_SIGN_ERROR = "验签失败";
    }

    interface userCode {
        int USERNAME_EXIT = -2001;
        int LOGIN_FAILURE = -2002;
        int OLD_PASSWORD_ERROR = -2003;
    }
    interface userMESSAGE {
        String USERNAME_EXIT = "用户名已存在";
        String LOGIN_FAILURE = "用户名或密码错误";
        String OLD_PASSWORD_ERROR = "旧密码错误";
    }

    interface fileCode {
        int IMAGE_FILE_TYPE_ERROR = -3001;
        int IMAGE_FILE_SIZE_ERROR = -3002;
        int IMAGE_FILE_EMPTY_ERROR = -3003;
    }
    interface fileMessage {
        String IMAGE_FILE_TYPE_ERROR = "文件类型只能是png,jpg,gif";
        String IMAGE_FILE_SIZE_ERROR = "文件大小只能是小于1Mb的文件";
        String IMAGE_FILE_EMPTY_ERROR = "文件不能是空";
    }
}
