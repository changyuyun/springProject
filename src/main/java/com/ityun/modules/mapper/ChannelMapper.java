package com.ityun.modules.mapper;

import com.ityun.modules.entity.Channel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChannelMapper {
    @Select("select id, key_, name, thumbnail from mto_channel where status=0 order by weight desc")
    List<Channel> list();

}
