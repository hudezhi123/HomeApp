package com.hudezhi.freedom.homeapp.live_video.presenter;

/**
 * Created by boy on 2017/8/11.
 */

public abstract class BasePresenter {

    public abstract void onViewDestroy();

    public void onViewStart(){}

    public void onViewResume(){}

    public void onViewPause(){}

    public void onViewStop(){}


}
