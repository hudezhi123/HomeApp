package com.hudezhi.freedom.homeapp.video;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;


import com.bumptech.glide.Glide;
import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.base.BaseActivity;
import com.hudezhi.freedom.homeapp.video.pager.LocalVideoPager;
import com.hudezhi.freedom.homeapp.video.pager.MineVideoPager;
import com.hudezhi.freedom.homeapp.video.pager.NetVideoPager;
import com.hudezhi.freedom.homeapp.video.videobase.BasePager;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup group;
    private List<BasePager> basePagerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        init();

    }

    private void init() {
        initView();
        basePagerList = new ArrayList<>();
        basePagerList.add(new LocalVideoPager(this));
        basePagerList.add(new NetVideoPager(this));
        basePagerList.add(new MineVideoPager(this));
        group.setOnCheckedChangeListener(this);
        setFragment(0);
    }


    private void initView() {
        initTitleBar();
        logo.setBackgroundResource(R.mipmap.video_logo_title);
        group = (RadioGroup) findViewById(R.id.group_video);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int position = -1;
        switch (checkedId) {
            case R.id.radio_video_local:
                position = 0;
                break;
            case R.id.radio_video_net:
                position = 1;
                break;
            case R.id.radio_video_mine:
                position = 2;
                break;
        }
        setFragment(position);
    }

    private void setFragment(final int position) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.video_content, new Fragment() {
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                BasePager basepager = getBasePager(position);
                if (basepager != null) {
                    return basepager.rootView;
                }
                return null;
            }
        });
        transaction.commit();
    }

    /**
     * 获取相应的pager
     *
     * @param position
     * @return
     */
    private BasePager getBasePager(int position) {
        BasePager basepager = basePagerList.get(position);
        if (basepager != null) {
            basepager.initData();
            return basepager;
        }
        return null;
    }


}
