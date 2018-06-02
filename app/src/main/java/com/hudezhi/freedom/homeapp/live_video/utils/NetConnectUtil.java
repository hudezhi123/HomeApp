package com.hudezhi.freedom.homeapp.live_video.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hudezhi.freedom.homeapp.live_video.bean.NetworkBean;

/**
 * Created by boy on 2017/8/11.
 */

public class NetConnectUtil {

    public static final int NET_STATE_NONE = -1;
    public static final int NET_STATE_WIFI = 1;
    public static final int NET_STATE_MOBILE_NET = 0;

    public static NetworkBean getNetworkInfo(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        NetworkBean networkBean = new NetworkBean();
        if (networkInfo != null && networkInfo.isConnected()) {
            networkBean.setType(networkInfo.getType());
            networkBean.setName(networkInfo.getExtraInfo());

        } else {
            networkBean.setType(NET_STATE_NONE);
            networkBean.setName("");
        }
        return networkBean;
    }
}
