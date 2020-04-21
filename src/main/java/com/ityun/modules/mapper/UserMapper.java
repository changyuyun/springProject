package com.ityun.modules.mapper;

import com.ityun.modules.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;


/**
 * mybatis user
 */
@Mapper
public interface UserMapper {
    @Select("select * from mto_user where username= #{username} and password= #{password}")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Insert("insert into mto_user (username, name, avatar, email, password, status, created, gender, comments, posts, signature) values (#{username}, #{name}, #{avatar}, #{email}, #{password}, #{status}, #{created}, #{gender}, #{comments}, #{posts}, #{signature})")
    int addUser(@Param("username") String username,
                @Param("name") String name,
                @Param("avatar") String avatar,
                @Param("email") String email,
                @Param("password") String password,
                @Param("status") int status,
                @Param("created") Date created,
                @Param("gender") int gender,
                @Param("comments") int comments,
                @Param("posts") int posts,
                @Param("signature") String signature);

    @Update("update mto_user set avatar=#{avatar}, signature=#{signature}, name=#{name},email=#{email},gender=#{gender} where id=#{id}")
    int updateUser(@Param("id") int id,
                   @Param("avatar") String avatar,
                   @Param("signature") String signature,
                   @Param("name") String name,
                   @Param("email") String email,
                   @Param("gender") int gender);
}
