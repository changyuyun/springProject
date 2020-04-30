package com.ityun.modules.service.impl;

import com.ityun.modules.entity.Post;
import com.ityun.modules.mapper.PostMapper;
import com.ityun.modules.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;
    @Override
    public long listCount(int channel) {
        return postMapper.countAll(channel);
    }

    @Override
    public List<Post> list(int start, int limit, int channel) {
        return postMapper.getListByPager(start, limit, channel);
    }
}
