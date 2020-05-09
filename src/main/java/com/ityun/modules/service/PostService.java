package com.ityun.modules.service;

import com.ityun.modules.entity.ArticlePost;

import java.util.List;

public interface PostService {

    long listCount(int channel);

    List<ArticlePost> list(int start, int limit, int channel);
}
