package com.ityun.web.controller.site;

import com.ityun.base.lang.Result;
import com.ityun.base.lang.ResultConst;
import com.ityun.web.controller.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorite")
@Validated
public class FavoriteController extends BaseController {
    @PostMapping("/action")
    public Result action() {
        return Result.success(ResultConst.commonMessage.COMMON_SUCCESS, null);
    }
}
