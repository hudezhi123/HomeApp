package com.hudezhi.freedom.homeapp.video.utils;

import android.os.Environment;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by boy on 2017/8/24.
 */

public class DownloadUtils {
    public static void downloadFile(String fileUrl, int start) throws Exception {
        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            int length = conn.getContentLength();   //获取文件长度
            File file = new File(getFilePath());
            RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
            accessFile.setLength(length);
            accessFile.close();
        }
    }

    private static String getFilePath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String rooPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            File file = new File(rooPath, "HomApp/video");
            if (!file.exists()) {
                file.mkdirs();
            }
            return file.getAbsolutePath();
        }
        return null;
    }
}
