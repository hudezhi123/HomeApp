package com.hudezhi.freedom.homeapp.person.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hudezhi.freedom.homeapp.MainApplication;
import com.hudezhi.freedom.homeapp.person.bean.UserInfoBean;

/**
 * Created by boy on 2017/7/31.
 */

public class UserInfoModel implements IUserInfoModel {
    public static Context context;
    SharedPreferences share = null;

    @Override
    public UserInfoBean loadLoginData() {
        share = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        String jsonResult = share.getString("UserInfo", "");
        if (!TextUtils.isEmpty(jsonResult)) {
            UserInfoBean userInfo = new Gson().fromJson(jsonResult, UserInfoBean.class);
            return userInfo;
        }
        return null;
    }

    @Override
    public void saveLoginData(String userName, String password) throws Exception {
        UserInfoBean userInfoBean = new UserInfoBean(userName, password);
        String jsonResult = new Gson().toJson(userInfoBean);
        share = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("UserInfo", jsonResult);
        editor.putBoolean("isRegister", true);
        editor.commit();
    }
}
