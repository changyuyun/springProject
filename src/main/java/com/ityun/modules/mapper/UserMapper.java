package com.ityun.modules.mapper;

import com.ityun.modules.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * mybatis user
 */
@Mapper
public interface UserMapper {
    @Select("select * from mto_user where username= #{username} and password= #{password}")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
