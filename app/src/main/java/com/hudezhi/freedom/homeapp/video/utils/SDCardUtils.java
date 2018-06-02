package com.hudezhi.freedom.homeapp.video.utils;

import android.content.Context;
import android.os.Environment;

/**
 * Created by hudezhi on 2017/3/13.
 */

public class SDCardUtils {
    /**
     * 判断sdcard是否挂在
     *
     * @return
     */
    public static boolean isSDCardMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 得到sdcard的根目录
     *
     * @return
     */
    public static String getSDCardRootPath() {
        if (isSDCardMounted()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

}
