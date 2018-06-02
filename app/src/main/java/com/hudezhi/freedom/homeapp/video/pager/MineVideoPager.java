package com.hudezhi.freedom.homeapp.video.pager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.video.videobase.BasePager;

/**
 * Created by hudezhi on 2017/3/12.
 * 我的页面
 */

public class MineVideoPager extends BasePager {
    private TextView textView;

    public MineVideoPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textView = new TextView(context);

        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("wodeweangye");
    }
}
