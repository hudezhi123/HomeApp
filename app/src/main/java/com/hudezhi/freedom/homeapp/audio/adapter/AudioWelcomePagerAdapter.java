package com.hudezhi.freedom.homeapp.audio.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hudezhi on 2017/3/30.
 */

public class AudioWelcomePagerAdapter extends PagerAdapter {
    private Context context;
    private List<View> viewList;

    public AudioWelcomePagerAdapter(Context context, List<View> viewList) {
        this.context = context;
        this.viewList = viewList;
    }


    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
