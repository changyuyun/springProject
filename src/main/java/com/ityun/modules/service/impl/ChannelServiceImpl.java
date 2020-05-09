package com.ityun.modules.service.impl;

import com.ityun.modules.entity.Channel;
import com.ityun.modules.mapper.ChannelMapper;
import com.ityun.modules.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    private ChannelMapper channelMapper;
    @Override
    public List<Channel> list() {
        return channelMapper.list();
    }
}
