package com.hudezhi.freedom.homeapp.video.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boy on 2017/8/23.
 */

public class FileUrl {
    public static String SERVER_ADDRESS = "";
    public static final String ALL_DATA_FILE = "/alldata/file/";
    public static final String ALL_DATA_IMG = "/alldata/img/";
    public static final String ALL_DATA_MOVIE = "/alldata/movie/";

    public static final int URL_TYPE = 0;
    public static final int URL_TYPE_FILE = 1;
    public static final int URL_TYPE_IMG = 2;
    public static final int URL_TYPE_AUDIO = 3;
    public static final int URL_TYPE_VIDEO = 4;

    public static String getBaseServer() {
        return "http://" + SERVER_ADDRESS + ":8080";
    }

    public static List<String> getUrlList(@SuppressWarnings("file type") int type) {
        List<String> urlList = new ArrayList<>();
        switch (type) {
            case URL_TYPE_FILE:
                urlList = getFileUrl(urlList);
                break;
            case URL_TYPE_IMG:
                urlList = getImgUrl(urlList);
                break;
            case URL_TYPE_AUDIO:
                urlList = getAudioUrl(urlList);
                break;
            case URL_TYPE_VIDEO:
                urlList = getVideoUrl(urlList);
                break;
        }
        return urlList;
    }

    private static List<String> getVideoUrl(List<String> urlList) {
        urlList.add("519745_MP4.mp4");
        urlList.add("608480_MP4.mp4");
        urlList.add("626867_MP4.mp4");
        urlList.add("854396_MP4.mp4");
        urlList.add("1033391_MP4.mp4");
        urlList.add("1038994_MP4.mp4");
        urlList.add("1106159_MP4.mp4");
        urlList.add("363310_MP4.mp4");
        urlList.add("323817_MP4.mp4");
        urlList.add("239520_MP4.mp4");
        urlList.add("236687_MP4.mp4");
        urlList.add("130581_MP4.mp4");
        urlList.add("110247_MP4.mp4");
        urlList.add("102861_MP4.mp4");
        urlList.add("82623_MP4.mp4");
        urlList.add("80403_MP4.mp4");
        return urlList;
    }

    private static List<String> getFileUrl(List<String> urlList) {
        urlList.add("mysql-5.7.17.msi");
        return urlList;
    }

    private static List<String> getImgUrl(List<String> urlList) {
        urlList.add("");
        return urlList;
    }

    private static List<String> getAudioUrl(List<String> urlList) {

        return urlList;
    }
}
