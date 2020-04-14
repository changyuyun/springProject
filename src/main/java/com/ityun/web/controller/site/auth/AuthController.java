package com.ityun.web.controller.site.auth;

import com.ityun.base.lang.Result;
import com.ityun.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 身份识别相关接口
 */
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login() {
        return null;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register() {
        return null;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Result logout() {
        return null;
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public Result status() {
        return null;
    }
}
