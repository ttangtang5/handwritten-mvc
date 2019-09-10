package com.tang.entity;

/**
 * @FileName: User
 * @Author: 16
 * @Date: 2018/8/29 20:26
 * @Description:
 */
public class User {

    private String id;

    private String name;

    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
