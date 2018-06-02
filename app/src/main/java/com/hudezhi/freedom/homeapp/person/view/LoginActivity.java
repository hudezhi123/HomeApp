package com.hudezhi.freedom.homeapp.person.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.person.MainPrivateActivity;
import com.hudezhi.freedom.homeapp.person.model.UserInfoModel;
import com.hudezhi.freedom.homeapp.person.presenter.UserInfoPresenter;
import com.hudezhi.freedom.homeapp.utils.AnnotateUtil;
import com.hudezhi.freedom.homeapp.utils.BindView;


public class LoginActivity extends BaseActivity implements ILoginActivity {

    private UserInfoPresenter userPresenter;
    private SharedPreferences share;

    @BindView(id = R.id.edit_UserName)
    private EditText editUserName;
    @BindView(id = R.id.edit_UserPassword)
    private EditText editUserPassword;
    @BindView(id = R.id.text_register)
    private TextView textToRegister;
    @BindView(id = R.id.linear_text_register)
    private LinearLayout linearRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_person);
        UserInfoModel.context = this;
        AnnotateUtil.initBindView(this);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        share = getSharedPreferences("User", Context.MODE_PRIVATE);
        boolean is = share.getBoolean("isRegister", false);
        if (is) {
            linearRegister.setVisibility(View.GONE);
        } else {
            linearRegister.setVisibility(View.VISIBLE);
        }
        userPresenter = new UserInfoPresenter(this);
    }

    //登录
    public void onLogin(View view) {
        String userName = editUserName.getText().toString().trim();
        String password = editUserPassword.getText().toString().trim();
        userPresenter.login(userName, password);
    }

    //注册
    public void onToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginSuccess() {
        showToast("登录成功！");
        Intent intent = new Intent(this, MainPrivateActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void loginFail(String message) {
        showToast(message);
    }

    @Override
    public void LoginError(String message) {
        showToast(message);
    }
}
