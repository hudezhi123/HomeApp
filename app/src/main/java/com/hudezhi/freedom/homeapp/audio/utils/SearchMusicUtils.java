package com.hudezhi.freedom.homeapp.audio.utils;

import android.os.Handler;
import android.os.Message;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by boy on 2017/6/13.
 */

public class SearchMusicUtils {
    private static SearchMusicUtils mInstance;
    private OnSearchResultListener mListener;

    public synchronized static SearchMusicUtils getInstance() {
        if (mInstance == null) {
            try {
                mInstance = new SearchMusicUtils();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return mInstance;
    }

    private SearchMusicUtils() throws ParserConfigurationException {

    }

    public SearchMusicUtils setListener(OnSearchResultListener listener) {
        mListener = listener;
        return this;
    }

    public void search(final String key, final int page) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                }
            }
        };
    }

    public interface OnSearchResultListener {

    }
}
