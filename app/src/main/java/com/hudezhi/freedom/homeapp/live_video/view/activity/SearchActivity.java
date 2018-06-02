package com.hudezhi.freedom.homeapp.live_video.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.live_video.presenter.BasePresenter;

public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public BasePresenter getBasePresenter() {
        return null;
    }
}
