package com.ityun.modules.service;

import com.ityun.modules.entity.ArticlePost;

import java.util.List;

public interface PostService {

    long listCount(int channel);

    List<ArticlePost> list(int start, int limit, int channel);

    long listCountByUser(int authorId);

    List<ArticlePost> listByUser(int start, int limit, int authorId);

    List<ArticlePost> lastedList(int limit);

    List<ArticlePost> hottestList(int limit);

    List<ArticlePost> search(String keyword);
}
