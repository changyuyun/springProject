package com.ityun.modules.mapper;

import com.ityun.modules.entity.ArticlePost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper {

    @Select("<script>"
            +"select p.*, u.name as author, u.avatar from mto_post as p left join mto_user as u on p.author_id=u.id  where 1=1"
            +"<if test='channel != 0'>"
            +"and channel_id=#{channel}"
            +"</if>"
            +"order by created desc limit #{start},#{limit}"
            +"</script>")
    List<ArticlePost> getListByPager(@Param("start") int start, @Param("limit") int limit, @Param("channel") int channel);

    @Select("<script>"
            +"select count(*) as total from mto_post where 1=1"
            +"<if test='channel != 0'>"
            +"and channel_id=#{channel}"
            +"</if>"
            +"</script>")
    Long countAll(@Param("channel") Integer channel);


}
