package com.ityun.modules.service.impl;

import com.ityun.config.SiteConfig;
import com.ityun.modules.entity.ArticlePost;
import com.ityun.modules.mapper.PostMapper;
import com.ityun.modules.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private SiteConfig siteConfig;

    @Override
    public long listCount(int channel) {
        return postMapper.countAll(channel);
    }

    @Override
    public List<ArticlePost> list(int start, int limit, int channel) {
        List<ArticlePost> listByPager = postMapper.getListByPager(start, limit, channel);
        return formatList(listByPager);
    }

    @Override
    public List<ArticlePost> listByUser(int start, int limit, int authorId) {
        List<ArticlePost> listByUser = postMapper.getListByUser(start, limit, authorId);
        return formatList(listByUser);
    }

    @Override
    public long listCountByUser(int authorId) {
        return postMapper.countListByUser(authorId);
    }

    /**
     * 处理列表数据
     * @param list
     * @return
     */
    private List<ArticlePost> formatList(List<ArticlePost> list) {
        if (list == null) {
            return null;
        }
        //处理头像 添加网站域名
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ArticlePost articlePost : list) {
            String avatar = siteConfig.getUrl() + articlePost.getAvatar();
            articlePost.setAvatar(avatar);
            String format = sdf.format(articlePost.getCreated());
            articlePost.setCreated_at(format);
        }
        return list;
    }
}
