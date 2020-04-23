package com.ityun.base.lang;

import java.io.Serializable;

/**
 * json响应格式
 */
public class Result implements Serializable {

    private int code;

    private String message;

    private Object data;

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result success(String message, Object data) {
        return new Result(ResultConst.commonCode.COMMON_SUCCESS, message, data);
    }

    public static Result successMessage(String message) {
        return new Result(ResultConst.commonCode.COMMON_SUCCESS, message);
    }

    public static Result failure(String message) {
        return new Result(ResultConst.commonCode.COMMON_FAILURE, message);
    }
    public static Result failure(int code, String message) {
        return new Result(code, message);
    }

}
