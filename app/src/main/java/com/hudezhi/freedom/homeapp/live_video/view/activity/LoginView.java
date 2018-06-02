package com.hudezhi.freedom.homeapp.live_video.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.live_video.presenter.BasePresenter;
import com.hudezhi.freedom.homeapp.live_video.presenter.LoginPresenter;
import com.hudezhi.freedom.homeapp.live_video.view.view_interface.ILoginView;
import com.hudezhi.freedom.homeapp.utils.BindView;

import java.util.HashMap;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;


public class LoginView extends BaseActivity implements ILoginView {

    @BindView(id = R.id.edit_pass_login)
    private EditText password;
    @BindView(id = R.id.edit_count_login)
    private EditText userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new LoginPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_view;
    }

    @Override
    public void initIntentData() {

    }

    private TextWatcher textChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(s)) {
                if (('a' <= s.charAt(s.length() - 1) && 'z' >= s.charAt(s.length() - 1)) || ('0' <= s.charAt(s.length() - 1) && '9' >= s.charAt(s.length() - 1))) {
                    // do nothing
                } else {
                    showToast("非法字符，允许小写字母和数字！");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void initView() {
        userName.addTextChangedListener(textChange);
        password.addTextChangedListener(textChange);
    }

    @Override
    public void initData() {

    }

    public void onBackClick(View view) {

    }

    public void onSelfLogin(View view) {
        showProgressDialog("正在登录...");
        String pass = password.getText().toString();
        String user = userName.getText().toString();
        ((LoginPresenter) mPresenter).SelfLogin(user, pass);
    }

    public void onOtherLogin(View view) {
        showProgressDialog("正在登录...", true);
        ((LoginPresenter) mPresenter).otherLogin(view.getId());
    }

    public void onRegister(View view) {
        ((LoginPresenter) mPresenter).Register(view);
    }


    @Override
    public BasePresenter getBasePresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void loginFailed(String message) {
        closeProgressDialog();
        showToast(message);
    }

    @Override
    public void loginSuccess() {
        closeProgressDialog();
        Intent intent = new Intent(this, GuideActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void loginError(String s) {
        closeProgressDialog();
        showToast(s);
    }

    @Override
    public void onProgress(int i, String s) {

    }


}
