package com.ityun.modules.mapper;

import com.ityun.modules.entity.ArticlePost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper {

    @Select("<script>"
            +"select p.*, u.name as author, u.avatar, c.name as channel_name from mto_post as p left join mto_user as u on p.author_id=u.id left join mto_channel as c on p.channel_id=c.id  where 1=1"
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

    @Select("<script>"
            +"select p.*, u.name as author, u.avatar, c.name as channel_name from mto_post as p left join mto_user as u on p.author_id=u.id left join mto_channel as c on p.channel_id=c.id  where author_id=#{authorId}"
            +" order by created desc limit #{start},#{limit}"
            +"</script>")
    List<ArticlePost> getListByUser(@Param("start") int start, @Param("limit") int limit, @Param("authorId") int authorId);

    @Select("<script>"
            +"select count(*) as total from mto_post where author_id=#{authorId}"
            +"</script>")
    Long countListByUser(@Param("authorId") int authorId);

    @Select("select id, title, summary, author_id, channel_id, created, views from mto_post order by created desc limit #{limit}")
    List<ArticlePost> getListByTime(@Param("limit") int limit);

    @Select("select id, title, summary, author_id, channel_id, created, views from mto_post order by views desc limit #{limit}")
    List<ArticlePost> getListByViews(@Param("limit") int limit);

    @Select("select p.*, u.name as author, u.avatar, c.name as channel_name from mto_post as p left join mto_user as u on p.author_id=u.id left join mto_channel as c on p.channel_id=c.id  where p.title like concat('%', #{keyword}, '%') order by p.created desc")
    List<ArticlePost> getListByKeyword(@Param("keyword") String keyword);
}
