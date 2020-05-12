package com.ityun.web.controller.site;

import com.ityun.base.lang.Result;
import com.ityun.base.lang.ResultConst;
import com.ityun.modules.entity.ArticlePost;
import com.ityun.modules.entity.Pager;
import com.ityun.modules.service.PostService;
import com.ityun.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
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
        List<ArticlePost> list = postService.list(start, limit, channel);
        Pager pager = getPager(page, total, totalPage, list);
        return Result.success(ResultConst.commonMessage.COMMON_SUCCESS, pager);
    }

    /**
     * 作者文章列表
     * @param authorId
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/user/list")
    public Result userList(@NotBlank(message = "authorId is must") String authorId, String currentPage, String pageSize) {
        if (authorId == null) {
            authorId = "0";
        }
        if (currentPage == null) {
            currentPage = "1";
        }
        if (pageSize == null) {
            pageSize = "10";
        }
        int id = Integer.parseInt(authorId);
        int page = Integer.parseInt(currentPage);
        int limit = Integer.parseInt(pageSize);
        int start = (page - 1) * limit;

        long totalRecord = postService.listCountByUser(id);
        int total = (int)totalRecord;
        int totalPage = (total + limit-1)/limit;

        List<ArticlePost> articlePosts = postService.listByUser(start, limit, id);
        Pager pager = getPager(page, total, totalPage, articlePosts);
        return Result.success(ResultConst.commonMessage.COMMON_SUCCESS, pager);
    }

    /**
     * 文章详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public Result detail(@NotBlank(message = "id is must") String id) {//TODO:
        Integer articleId = Integer.parseInt(id);
        System.out.println(articleId);
        return Result.success(ResultConst.commonMessage.COMMON_SUCCESS, null);
    }

    /**
     * 最新文章
     * @return
     */
    @GetMapping("/latest")
    public Result latest() {
        int limit = 3;
        List<ArticlePost> articlePosts = postService.lastedList(limit);
        return Result.success(ResultConst.commonMessage.COMMON_SUCCESS, articlePosts);
    }

    /**
     * 热门文章
     * @return
     */
    @GetMapping("/hottest")
    public Result hottest() {
        int limit = 3;
        List<ArticlePost> articlePosts = postService.hottestList(limit);
        return Result.success(ResultConst.commonMessage.COMMON_SUCCESS, articlePosts);
    }

    /**
     * 搜索文章
     * @return
     */
    @GetMapping("/search")
    public Result search(String keyword) {
        List<ArticlePost> result = new ArrayList();
        if (keyword == null || "".equals(keyword)) {
            return Result.success(ResultConst.commonMessage.COMMON_SUCCESS, result);
        }
        result = postService.search(keyword);
        return Result.success(ResultConst.commonMessage.COMMON_SUCCESS, result);
    }
}
