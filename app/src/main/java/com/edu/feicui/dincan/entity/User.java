package com.edu.feicui.dincan.entity;

/**
 * Created by user on 2016/11/28.
 */

public class User {
    private int id;
    private String account;
    private String password;
    private String name;
    private String gender;
    private int permission;
    private String remark;

    public User(int id, String remark, String gender, int permission, String name, String password, String account) {
        this.id = id;
        this.remark = remark;
        this.gender = gender;
        this.permission = permission;
        this.name = name;
        this.password = password;
        this.account = account;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
