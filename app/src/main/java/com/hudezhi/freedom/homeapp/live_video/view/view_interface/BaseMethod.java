package com.hudezhi.freedom.homeapp.live_video.view.view_interface;

import com.hudezhi.freedom.homeapp.live_video.presenter.BasePresenter;

/**
 * Created by boy on 2017/8/11.
 */

public interface BaseMethod {

    int getLayoutId();

    void initIntentData();

    void initView();

    void initData();

    BasePresenter getBasePresenter();

}
