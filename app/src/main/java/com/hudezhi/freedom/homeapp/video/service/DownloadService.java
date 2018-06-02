package com.hudezhi.freedom.homeapp.video.service;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.hudezhi.freedom.homeapp.video.bean.NetVideoBean;
import com.hudezhi.freedom.homeapp.video.bean.VideoDBHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

public class DownloadService extends Service {
    public static int mThreadMax = 1;
    private ThreadPoolExecutor fixedThreadPool = null;
    private List<Thread> threadList;

    private volatile boolean isStart = false;

    public DownloadService() {
        fixedThreadPool.getQueue();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        threadList = new ArrayList<>();
    }

    public class DownloadBind extends Binder {

        public DownloadService getDownloadService() {
            return DownloadService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new DownloadBind();
    }



}
