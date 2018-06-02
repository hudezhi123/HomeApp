package com.hudezhi.freedom.homeapp.live_video.view.view_interface;

import android.app.Activity;

/**
 * Created by boy on 2017/8/15.
 */

public interface ILoginView{
    public void loginFailed(String message);

    public void loginSuccess();

    public void loginError(String s);

    public void onProgress(int i, String s);
}
