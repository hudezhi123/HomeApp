package com.hudezhi.freedom.homeapp.person.model;

import com.hudezhi.freedom.homeapp.person.bean.UserInfoBean;

/**
 * Created by boy on 2017/8/1.
 */

public interface IUserInfoModel {

    public UserInfoBean loadLoginData();

    public void saveLoginData(String userName, String password) throws Exception;
}
