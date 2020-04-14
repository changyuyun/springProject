package com.ityun.web.controller.site;

import com.ityun.base.lang.Result;
import com.ityun.base.utils.RedisUtils;
import com.ityun.web.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class IndexController extends BaseController {
    @Resource
    private RedisUtils redisUtils;
    @RequestMapping(value = {"/", "/index"})
    public Result home() {
        redisUtils.set("java-redis", "redis");
        return Result.successMessage("ok");
    }
}
