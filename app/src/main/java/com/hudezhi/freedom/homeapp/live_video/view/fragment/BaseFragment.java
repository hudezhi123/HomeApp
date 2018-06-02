package com.hudezhi.freedom.homeapp.live_video.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * Created by boy on 2017/8/11.
 */

public abstract class BaseFragment extends Fragment {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void initView(View view);

    public abstract void initData();

    public abstract void updateView();

//    public abstract Activity getAct();

}
