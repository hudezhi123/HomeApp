package com.hudezhi.freedom.homeapp.live_video.view.fragment;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.audio.bean.Constant;
import com.hudezhi.freedom.homeapp.live_video.bean.Constants;
import com.hudezhi.freedom.homeapp.live_video.bean.Flags;
import com.hudezhi.freedom.homeapp.live_video.view.activity.GuideActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment extends BaseFragment {

    private Activity mActivity;

    public LiveFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live, container, false);
    }


    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void updateView() {
        mActivity = getActivity();
        ((GuideActivity) mActivity).setItemVisible(Flags.LIVE);
    }
}
