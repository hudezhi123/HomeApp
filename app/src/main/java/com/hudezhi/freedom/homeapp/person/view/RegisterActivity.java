package com.hudezhi.freedom.homeapp.person.view;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.person.model.UserInfoModel;
import com.hudezhi.freedom.homeapp.person.presenter.UserInfoPresenter;
import com.hudezhi.freedom.homeapp.utils.AnnotateUtil;
import com.hudezhi.freedom.homeapp.utils.BindView;

public class RegisterActivity extends BaseActivity implements IRegisterActivity {

    private UserInfoPresenter userPresenter;

    @BindView(id = R.id.edit_UserName_register)
    private EditText editUserName;
    @BindView(id = R.id.edit_UserPassword_register)
    private EditText editUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        TextView title = (TextView) findViewById(R.id.text_title_private_bar);
        title.setVisibility(View.VISIBLE);
        title.setText("注 册");
        userPresenter = new UserInfoPresenter(this);
    }

    //注册
    public void onRegister(View view) {
        String userName = editUserName.getText().toString().trim();
        String password = editUserPassword.getText().toString().trim();
        userPresenter.register(userName, password);
    }

    @Override
    public void registerSuccess() {
        showToast("注册成功！");
        this.finish();
    }

    @Override
    public void registerFail(String message) {
        showToast(message);
    }

    @Override
    public void registerError(String message) {
        showToast(message);
    }
}
