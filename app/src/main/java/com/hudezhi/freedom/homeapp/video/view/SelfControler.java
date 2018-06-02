package com.hudezhi.freedom.homeapp.video.view;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by hudezhi on 2017/3/17.
 */

public class SelfControler {
    private TextView title, systemTime, passTime, totalTime;
    private ImageView power, backActivity, preVideoItem, nextVideoItem, light, voice, screen;
    private CheckBox pauseOrPlay;
    private SeekBar videoProgress;
    private Context mContext;

    public SelfControler(Context context) {
        this.mContext = context;
    }

    /**
     * 初始化controler的布局
     */
    private void initView(){

    }

    public void nextItem(){

    }
}
