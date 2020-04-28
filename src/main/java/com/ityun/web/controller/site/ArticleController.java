package com.ityun.web.controller.site;

import com.ityun.base.lang.Result;
import com.ityun.base.lang.ResultConst;
import com.ityun.web.controller.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/article")
@Validated
public class ArticleController extends BaseController {
    /**
     * 首页文章列表
     * @return
     */
    @GetMapping("/list")
    public Result list(String channelId, String currentPage, String pageSize) {
        //TODO:按分页的形式返回数据 channel_id
        if (channelId == null) {
            channelId = "0";
        }
        if (currentPage == null) {
            currentPage = "1";
        }
        if (pageSize == null) {
            pageSize = "10";
        }
        int channel = Integer.parseInt(channelId);
        int page = Integer.parseInt(currentPage);
        int limit = Integer.parseInt(pageSize);
        int start = (page - 1) * limit;

        return Result.successMessage(ResultConst.commonMessage.COMMON_SUCCESS);
    }
}
