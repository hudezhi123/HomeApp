package com.hudezhi.freedom.homeapp.live_video.view.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.live_video.bean.Flags;
import com.hudezhi.freedom.homeapp.live_video.view.activity.GuideActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends BaseFragment {

    private Activity mActivity;

    public FindFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_find, container, false);
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
        ((GuideActivity) mActivity).setItemVisible(Flags.FIND);
    }
}
