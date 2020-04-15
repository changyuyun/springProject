package com.ityun.web.controller.site.auth;

import com.ityun.base.lang.Result;
import com.ityun.modules.entity.User;
import com.ityun.modules.group.UserLogin;
import com.ityun.modules.group.UserRegister;
import com.ityun.web.controller.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 身份识别相关接口
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseController {

    @PostMapping(value = "/login")
    public Result login(@Validated({UserLogin.class}) @RequestBody(required = false) User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        return executeLogin(username, password);
    }

    @PostMapping(value = "/register")
    public Result register(@Validated({UserRegister.class}) @RequestBody(required = false) User user) {
        return Result.successMessage("ok");
    }

    @PostMapping(value = "/logout")
    public Result logout() {
        return null;
    }

    @PostMapping(value = "/status")
    public Result status() {
        return null;
    }
}
