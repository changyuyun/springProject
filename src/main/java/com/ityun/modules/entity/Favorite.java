package com.ityun.modules.entity;

import com.ityun.modules.group.FavoriteAct;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Favorite {
    private Integer id;

    private Date created;

    @NotNull(message = "post_id不能为空", groups = {FavoriteAct.class})
    private Integer post_id;

    private Integer user_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getPost_id() {
        return post_id;
    }

    public void setPost_id(Integer post_id) {
        this.post_id = post_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
