package com.ityun.web.controller.site;

import com.ityun.base.lang.Result;
import com.ityun.base.lang.ResultConst;
import com.ityun.modules.entity.Pager;
import com.ityun.modules.entity.Post;
import com.ityun.modules.service.PostService;
import com.ityun.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@Validated
public class ArticleController extends BaseController {
    @Autowired
    private PostService postService;
    /**
     * 首页文章列表
     * @return
     */
    @GetMapping("/list")
    public Result list(String channelId, String currentPage, String pageSize) {
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

        long totalRecord = postService.listCount(channel);
        int total = (int)totalRecord;
        int totalPage = (total + limit-1)/limit;
        List<Post> list = postService.list(start, limit, channel);
        Pager pager = getPager(page, total, totalPage, list);
        return Result.success(ResultConst.commonMessage.COMMON_SUCCESS, pager);
    }
}
