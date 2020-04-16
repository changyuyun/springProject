package com.ityun.modules.entity;

import com.ityun.modules.group.UserLogin;
import com.ityun.modules.group.UserRegister;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class User {
    private int id;

    @NotNull(message = "登陆名不能是空", groups = {UserLogin.class, UserRegister.class})
    @Size(min=3, max=10, message = "登陆名必须是3到10位", groups = {UserRegister.class})
    private String username;

    @NotNull(message = "昵称不能是空", groups = {UserRegister.class})
    @Size(min=3, max=10, message = "昵称必须是3到10位", groups = {UserRegister.class})
    private String name;

    private String avatar;

    @NotNull(message = "邮箱不能是空", groups = {UserRegister.class})
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",message = "邮箱格式错误", groups = {UserRegister.class})
    private String email;

    @NotNull(message = "密码不能是空", groups = {UserLogin.class, UserRegister.class})
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码 由数字和字母组成，并且要同时含有数字和字母，且长度要在8-16位之间。", groups = {UserRegister.class})
    private String password;

    private int status;

    private Date created;

    private Date updated;

    private Date last_login;

    private int gender;

    private int role_id;

    private int comments;

    private int post;

    private String signature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
