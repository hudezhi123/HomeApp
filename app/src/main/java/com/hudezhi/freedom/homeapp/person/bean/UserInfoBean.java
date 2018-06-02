package com.hudezhi.freedom.homeapp.person.bean;

import java.io.Serializable;

/**
 * Created by boy on 2017/7/31.
 */

public class UserInfoBean implements Serializable {
    private String userName;
    private String password;

    public UserInfoBean() {
    }

    public UserInfoBean(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
