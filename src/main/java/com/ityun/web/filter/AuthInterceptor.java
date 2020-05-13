package com.ityun.web.filter;

import com.google.gson.Gson;
import com.ityun.base.lang.Result;
import com.ityun.base.lang.ResultConst;
import com.ityun.web.annotation.DisableAuth;
import com.ityun.web.controller.BaseController;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token拦截器
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
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
        /*BaseController baseController = new BaseController();
        int ret = baseController.token2Id(authToken);
        if (ret == 0) {
            setResponse(request, response, ResultConst.commonCode.COMMON_TOKEN_ERROR, ResultConst.commonMessage.COMMON_TOKEN_ERROR);
            return false;
        }*/
        request.setAttribute("token", authToken);
        //request.setAttribute("uid", ret);
        //return true;
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
