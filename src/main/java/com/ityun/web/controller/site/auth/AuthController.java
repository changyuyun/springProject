package com.ityun.web.controller.site.auth;

import com.ityun.base.lang.Result;
import com.ityun.modules.entity.User;
import com.ityun.modules.group.UserLogin;
import com.ityun.modules.group.UserRegister;
import com.ityun.web.annotation.DisableAuth;
import com.ityun.web.controller.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 身份识别相关接口
 */

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController extends BaseController {

    @PostMapping(value = "/login")
    @DisableAuth
    public Result login(@Validated({UserLogin.class}) @RequestBody(required = true) User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        return executeLogin(username, password);
    }

    @PostMapping(value = "/register")
    @DisableAuth
    public Result register(@Validated({UserRegister.class}) @RequestBody(required = true) User user) {
        return executeRegister(user);
    }

    @PostMapping(value = "/logout")
    public Result logout(@Validated @RequestBody(required = true) Map<String, Object> map) {
        if (!map.containsKey("token")){
            return Result.failure("token是必须参数");
        }
        if ("".equals(map.get("token"))) {
            return Result.failure("token不能是空");
        }
        String token = map.get("token").toString();
        return executeLogout(token);
    }
}
