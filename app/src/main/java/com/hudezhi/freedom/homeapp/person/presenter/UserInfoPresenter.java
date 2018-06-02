package com.hudezhi.freedom.homeapp.person.presenter;

import com.hudezhi.freedom.homeapp.person.bean.UserInfoBean;
import com.hudezhi.freedom.homeapp.person.model.IUserInfoModel;
import com.hudezhi.freedom.homeapp.person.model.UserInfoModel;
import com.hudezhi.freedom.homeapp.person.view.ILoginActivity;
import com.hudezhi.freedom.homeapp.person.view.IRegisterActivity;

/**
 * Created by boy on 2017/8/1.
 */

public class UserInfoPresenter {

    private IUserInfoModel iUserInfoModel;
    private ILoginActivity mLoginView;
    private IRegisterActivity mRegisterView;

    public UserInfoPresenter() {
    }

    public UserInfoPresenter(ILoginActivity mLoginView) {
        this.mLoginView = mLoginView;
        iUserInfoModel = new UserInfoModel();
    }

    public UserInfoPresenter(IRegisterActivity mRegisterView) {
        this.mRegisterView = mRegisterView;
        iUserInfoModel = new UserInfoModel();
    }


    //处理登录逻辑
    public void login(String userName, String password) {

        if (userName.isEmpty() || password.isEmpty()) {
            mLoginView.loginFail("账号和密码不能为空！");
        } else {
            UserInfoBean userInfo = iUserInfoModel.loadLoginData();
            if (userInfo == null) {
                mLoginView.LoginError("用户不存在，请注册！");
                return;
            }
            if (userName.equals(userInfo.getUserName())) {
                if (password.equals(userInfo.getPassword())) {
                    mLoginView.loginSuccess();
                } else {
                    mLoginView.loginFail("密码错误！");
                }
            } else {
                mLoginView.loginFail("用户名错误！");
            }
        }
    }

    //处理注册逻辑
    public void register(String userName, String password) {
        if (userName.isEmpty() || password.isEmpty()) {
            mRegisterView.registerFail("账号和密码不能为空！");
        } else {
            mRegisterView.registerSuccess();
            try {
                iUserInfoModel.saveLoginData(userName, password);
            } catch (Exception e) {
                mRegisterView.registerError("数据保存失败！");
            }
        }
    }
}
