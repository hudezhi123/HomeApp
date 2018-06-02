package com.hudezhi.freedom.homeapp.live_video.view.activity;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.hudezhi.freedom.homeapp.R;

import com.hudezhi.freedom.homeapp.live_video.presenter.BasePresenter;
import com.hudezhi.freedom.homeapp.live_video.presenter.RegisterPresenter;
import com.hudezhi.freedom.homeapp.live_video.view.view_interface.IRegisterView;
import com.hudezhi.freedom.homeapp.utils.BindView;


public class RegisterView extends BaseActivity implements IRegisterView {

    @BindView(id = R.id.edit_account_register)
    private EditText editUserName;
    @BindView(id = R.id.edit_pass_register)
    private EditText editPassword;
    @BindView(id = R.id.check_agree_register)
    private CheckBox agreeItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new RegisterPresenter(this);
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
    public int getLayoutId() {
        return R.layout.activity_register_view;
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initView() {
        editUserName.addTextChangedListener(textChange);
        editPassword.addTextChangedListener(textChange);
    }

    @Override
    public void initData() {

    }

    public void goNext(View view) {
        if (agreeItem.isChecked()) {
            String userName = editPassword.getText().toString();
            String password = editUserName.getText().toString();
            showProgressDialog("正在注册...");
            ((RegisterPresenter) mPresenter).Register(userName, password);
        } else {
            showToast("请勾选");
        }

    }

    @Override
    public BasePresenter getBasePresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void RegisterFail(String message) {
        closeProgressDialog();
        showToast(message);
    }

    @Override
    public void RegisterSuccess() {
        closeProgressDialog();
        showToast("注册成功！");
        this.finish();
    }

    @Override
    public void RegisterError(String message) {
        closeProgressDialog();
        showToast(message);
    }

}
