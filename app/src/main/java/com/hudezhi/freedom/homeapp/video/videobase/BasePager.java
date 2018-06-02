package com.hudezhi.freedom.homeapp.video.videobase;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by hudezhi on 2017/3/12.
 */

public abstract class BasePager {
    public final Context context;
    public View rootView;

    public BasePager(Context context) {
        this.context = context;
        rootView = initView();
    }

    /**
     * 初始化View
     */
    public abstract View initView();

    /**
     * 当子页面需要初始化数据，联网请求数据或者绑定数据的时候需要调用
     */
    public void initData() {

    }
}
