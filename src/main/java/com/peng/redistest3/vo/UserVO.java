package com.peng.redistest3.vo;

import java.io.Serializable;

public class UserVO implements Serializable {
    private static final long serialVersionUID=1;

    private long id;
    private String username;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
