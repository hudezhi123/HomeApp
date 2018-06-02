package com.hudezhi.freedom.homeapp.video.activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.utils.AnnotateUtil;
import com.hudezhi.freedom.homeapp.utils.BindView;
import com.hudezhi.freedom.homeapp.video.bean.FileUrl;
import com.hudezhi.freedom.homeapp.video.bean.VideoDBHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;

public class DownloadActivity extends Activity {

    private List<String> urlList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        AnnotateUtil.initBindView(this);
        urlList = FileUrl.getUrlList(FileUrl.URL_TYPE_VIDEO);
    }

}
