package com.hudezhi.freedom.homeapp.video.impl;

/**
 * Created by boy on 2017/8/25.
 */

public interface DownloadObserver {

    public void onStartDownload();

    public void onPauseDownload();

    public void onWaitDownload();

    public void onDeleteDownload();
}
