package com.ityun.modules.mapper;

import com.ityun.modules.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface PostMapper {

    Post getListByPager(@Param("start") int start, @Param("limit") int limit, @Param("channel") int channel);

    @Select("select count(*) as total from mto_post")
    Long countAll(@Param("channel") int channel);
}
