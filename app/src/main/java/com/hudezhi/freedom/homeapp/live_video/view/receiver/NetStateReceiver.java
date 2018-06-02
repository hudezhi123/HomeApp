package com.hudezhi.freedom.homeapp.live_video.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.baidu.mapapi.NetworkUtil;
import com.hudezhi.freedom.homeapp.live_video.bean.NetworkBean;
import com.hudezhi.freedom.homeapp.live_video.utils.NetConnectUtil;
import com.hudezhi.freedom.homeapp.live_video.view.activity.BaseActivity;

/**
 * Created by boy on 2017/8/11.
 */

public class NetStateReceiver extends BroadcastReceiver {

    public OnNetStateChangeListener onNetStateChangeListener = BaseActivity.onNetListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkBean networkBean = NetConnectUtil.getNetworkInfo(context.getApplicationContext());
            if (onNetStateChangeListener != null) {
                onNetStateChangeListener.onNetStateChanged(networkBean);
            }
        }
    }

    public interface OnNetStateChangeListener {
        public void onNetStateChanged(NetworkBean bean);
    }
}
