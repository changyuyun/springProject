package com.ityun.web.controller.site;

import com.ityun.base.lang.Result;
import com.ityun.web.controller.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * 用户相关接口
 */
@RestController
@RequestMapping("/api/user")
@Validated
public class UserController extends BaseController {

    @GetMapping("/info")
    public Result info(@NotBlank(message = "token is must") String token) {
        Object tokenInfo = getTokenInfo(token);
        if (tokenInfo == null) {
            return Result.failure("not login status");
        }
        return Result.success("ok", tokenInfo);
    }

    @PostMapping("edit")
    public Result edit() {
        return Result.successMessage("ok");
    }
}
