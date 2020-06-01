package com.ityun.web.filter;

import com.google.gson.Gson;
import com.ityun.base.lang.Result;
import com.ityun.base.lang.ResultConst;
import com.ityun.base.utils.RedisUtils;
import com.ityun.web.annotation.DisableAuth;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * token拦截器
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private String redisTokenUserKey = "User:token:%s";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //放行逻辑
        HandlerMethod method = (HandlerMethod) handler;
        DisableAuth auth = method.getMethod().getAnnotation(DisableAuth.class);
        if (isDisableAuth(auth)) {
            return super.preHandle(request, response, handler);
        }
        String authToken = getAuthToken(request);
        if (StringUtils.isEmpty(authToken)) {
            setResponse(request, response, ResultConst.commonCode.COMMON_TOKEN_NEEDLE, ResultConst.commonMessage.COMMON_TOKEN_NEEDLE);
            return false;
        }

        //token是否正确
        Integer ret = verifyAuthToken(authToken);
        if (ret == 0) {
            setResponse(request, response, ResultConst.commonCode.COMMON_TOKEN_ERROR, ResultConst.commonMessage.COMMON_TOKEN_ERROR);
            return false;
        }
        //校验通过：放行
        request.setAttribute("token", authToken);
        request.setAttribute("user_id", ret);
        return super.preHandle(request, response, handler);
    }

    private static boolean isDisableAuth(DisableAuth auth) {
        return auth != null;
    }

    /**
     * 获取http请求头中的token值
     * @param request
     * @return
     */
    private String getAuthToken(HttpServletRequest request) {
        return request.getHeader("token");
    }

    /**
     * 校验token是否正确
     * @param authToken
     * @return
     */
    private int verifyAuthToken(String authToken) {
        Integer ret = 0;
        Map<String, String> result = new HashMap<>();
        String key = String.format(redisTokenUserKey, authToken);
        Object info = RedisUtils.get(key);
        if (info != null) {
            Gson gson = new Gson();
            String json = gson.toJson(info);
            result = gson.fromJson(json, Map.class);
            for (Map.Entry<String, String> stringStringEntry : result.entrySet()) {
                if ("id".equals(stringStringEntry.getKey())) {
                    ret = Integer.parseInt(stringStringEntry.getValue());
                }
            }
        }
        return ret;
    }

    /**
     * 输出响应
     * @param request
     * @param response
     * @param code
     * @param message
     */
    private void setResponse(HttpServletRequest request, HttpServletResponse response, Integer code, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        Result failure = Result.failure(code, message);
        Gson gson = new Gson();
        String str = gson.toJson(failure);
        response.getWriter().print(str);
    }
}
