package com.hudezhi.freedom.homeapp.live_video.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.hudezhi.freedom.homeapp.live_video.view.view_interface.IRegisterView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by boy on 2017/8/15.
 */

public class RegisterPresenter extends BasePresenter {

    private IRegisterView registerView;
    private static final int REGISTER_SUCCESS = 1;
    private static final int REGISTER_ERROR = 2;
    private static final int REGISTER_FAIL = 3;

    public RegisterPresenter(IRegisterView registerView) {
        this.registerView = registerView;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REGISTER_SUCCESS:
                    registerView.RegisterSuccess();
                    break;
                case REGISTER_FAIL:
                    registerView.RegisterFail("该账号已经注册！");
                    break;
                case REGISTER_ERROR:
                    registerView.RegisterError("注册失败！");
                    break;
            }
        }
    };

    public void Register(final String userName, final String password) {
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().createAccount(userName, password);
                        mHandler.sendEmptyMessage(REGISTER_SUCCESS);
                    } catch (final HyphenateException e) {
                        if (e.getDescription().equals("User already exist")) {
                            mHandler.sendEmptyMessage(REGISTER_FAIL);
                        } else {
                            // TODO: 2017/8/15
                            mHandler.sendEmptyMessage(REGISTER_ERROR);
                        }
                    }
                }
            }).start();
        } else {
            registerView.RegisterError("用户名或密码不能为空！");
        }
    }


    @Override
    public void onViewDestroy() {
        registerView = null;
    }
}
