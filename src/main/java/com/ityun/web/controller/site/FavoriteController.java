package com.ityun.web.controller.site;

import com.ityun.base.lang.Result;
import com.ityun.base.lang.ResultConst;
import com.ityun.modules.entity.Favorite;
import com.ityun.modules.group.FavoriteAct;
import com.ityun.web.controller.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorite")
@Validated
public class FavoriteController extends BaseController {
    /**
     * 收藏（喜欢）文章
     * @param favorite
     * @return
     */
    @PostMapping("/action")
    public Result action(@Validated({FavoriteAct.class}) @RequestBody(required = true) Favorite favorite) {
        System.out.println("method");
        return Result.success(ResultConst.commonMessage.COMMON_SUCCESS, null);
    }
}
