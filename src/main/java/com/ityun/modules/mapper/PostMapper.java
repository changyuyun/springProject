package com.ityun.modules.mapper;

import com.ityun.modules.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper {

    @Select("<script>"
            +"select * from mto_post where 1=1"
            +"<if test='channel != 0'>"
            +"and channel_id=#{channel}"
            +"</if>"
            +"order by created desc limit #{start},#{limit}"
            +"</script>")
    List<Post> getListByPager(@Param("start") int start, @Param("limit") int limit, @Param("channel") int channel);

    @Select("<script>"
            +"select count(*) as total from mto_post where 1=1"
            +"<if test='channel != 0'>"
            +"and channel_id=#{channel}"
            +"</if>"
            +"</script>")
    Long countAll(@Param("channel") Integer channel);


}
