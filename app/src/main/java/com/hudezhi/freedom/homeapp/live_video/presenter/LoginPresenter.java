package com.hudezhi.freedom.homeapp.live_video.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.live_video.bean.OtherLoginInfoResult;
import com.hudezhi.freedom.homeapp.live_video.view.activity.LoginView;
import com.hudezhi.freedom.homeapp.live_video.view.activity.RegisterView;
import com.hudezhi.freedom.homeapp.live_video.view.view_interface.ILoginView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.json.JSONException;
import org.json.JSONTokener;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * Created by boy on 2017/8/15.
 */

public class LoginPresenter extends BasePresenter implements PlatformActionListener {

    private ILoginView loginView;

    public LoginPresenter(LoginView activity) {
        loginView = activity;
    }


    private static final int LOGIN_SUCCESS = 1;
    private static final int LOGIN_ERROR = 2;
    private static final int LOGIN_FAIL = 3;
    private static final int LOGIN_PROGRESS = 4;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGIN_SUCCESS:
                    loginView.loginSuccess();
                    break;
                case LOGIN_FAIL:
                    loginView.loginFailed((String) msg.obj);
                    break;
                case LOGIN_ERROR:
                    loginView.loginError((String) msg.obj);
                    break;
                case LOGIN_PROGRESS:
                    loginView.onProgress(msg.arg1, (String) msg.obj);
                    break;
            }
        }
    };

    public void otherLogin(int id) {
        switch (id) {
            case R.id.qq_login:
                qqLogin();
                break;
            case R.id.wechat_login:
                wechatLogin();
                break;
            case R.id.weibo_login:
                weiboLogin();
                break;
        }
    }

    private void qqLogin() {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        //每次都有需要输入帐号(false表示不用)
        qq.removeAccount(false);
        qq.setPlatformActionListener(this);
        //调用
        qq.SSOSetting(false);
        qq.showUser(null); //执行登陆，登录后再回调里面获得用户资料
    }

    private void weiboLogin() {
        return;
    }

    private void wechatLogin() {
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.removeAccount(false);
        wechat.setPlatformActionListener(this);
        wechat.SSOSetting(false);
        wechat.showUser(null);

    }

    public void SelfLogin(String userName, String password) {
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            EMClient.getInstance().login(userName, password, new EMCallBack() {
                @Override
                public void onSuccess() {
                    Log.i("Thread_success", Thread.currentThread().getName());
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    mHandler.sendEmptyMessage(LOGIN_SUCCESS);
                }

                @Override
                public void onError(int i, String s) {
                    Log.i("Thread_Error", Thread.currentThread().getName());
                    Message msg = mHandler.obtainMessage();
                    if (i == 204) {
                        msg.what = LOGIN_FAIL;
                        msg.obj = "该用户不存在！";
                    } else {
                        msg.what = LOGIN_ERROR;
                        msg.obj = s;
                    }
                    mHandler.sendMessage(msg);
                }

                @Override
                public void onProgress(int i, String s) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = LOGIN_PROGRESS;
                    msg.arg1 = i;
                    msg.obj = s;
                    mHandler.sendMessage(msg);
                }
            });
        } else {
            loginView.loginFailed("登录信息填写不完整！");
        }
    }

    public void Register(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setIcon(R.mipmap.push_logo);
        builder.setTitle("确定短信验证的方式注册？").setPositiveButton("确定", new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RegisterPage registerPage = new RegisterPage();
                        registerPage.setRegisterCallback(new EventHandler() {

                            //当前方法为主线程
                            public void afterEvent(int event, int result, Object data) {
                                // 解析注册结果
                                if (result == SMSSDK.RESULT_COMPLETE) {
                                    loginView.loginSuccess();
                                } else {
                                    loginView.loginFailed("验证码不正确！");
                                }
                            }
                        });
                        registerPage.show(view.getContext());
                    }
                }
        ).setNegativeButton("其他", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(view.getContext(), RegisterView.class);
                ((LoginView) loginView).startActivityForResult(intent, 990);
            }
        });
        builder.show();
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

        String info = platform.getDb().exportData();
        OtherLoginInfoResult other = null;
        if (!TextUtils.isEmpty(info)) {
            other = new Gson().fromJson(info, OtherLoginInfoResult.class);
//            other.getIcon();
//            String nickName = other.getNickname();
            String userName = other.getUserID().toLowerCase();
            String password = other.getToken().toLowerCase();
            try {
                EMClient.getInstance().createAccount(userName, password);
                loginView.loginSuccess();
            } catch (HyphenateException e) {
                if (e.getDescription().equals("User already exist")) {
                    loginView.loginSuccess();
                } else {
                    loginView.loginFailed("登录失败！");
                }
            }
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        loginView.loginError("登录失败！");
    }

    @Override
    public void onCancel(Platform platform, int i) {
        loginView.loginError("登录取消！");
    }

    @Override
    public void onViewDestroy() {
        loginView = null;
    }
}
