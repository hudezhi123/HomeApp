package com.hudezhi.freedom.homeapp.video.pager;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.video.VideoActivity;
import com.hudezhi.freedom.homeapp.video.activity.PlayerActivity;
import com.hudezhi.freedom.homeapp.video.adapter.LocalVideoAdapter;
import com.hudezhi.freedom.homeapp.video.bean.LocalVideoBean;
import com.hudezhi.freedom.homeapp.video.impl.ItemClickCallBack;
import com.hudezhi.freedom.homeapp.video.videobase.BasePager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hudehzi on 2017/3/12.
 * 本地视频页面
 */

public class LocalVideoPager extends BasePager implements ItemClickCallBack {
    private SharedPreferences sharedPreferences;
    private RecyclerView localRecyclerView;
    //    private ProgressBar loadProgress;
    private TextView tips;
    private List<LocalVideoBean> videoList;
    private LinearLayoutManager manager;
    private LocalVideoAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (videoList != null && videoList.size() > 0) {
                adapter.setList(videoList);
                adapter.setItemClickCallBack(LocalVideoPager.this);
            } else {
                //文本显示
                tips.setVisibility(View.VISIBLE);
            }

        }
    };

    public LocalVideoPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.localvideo_layout, null);
        sharedPreferences = context.getSharedPreferences("recode", Context.MODE_PRIVATE);
        tips = (TextView) view.findViewById(R.id.text_local_video_tips);
        tips.setVisibility(View.GONE);
        //文本隐藏
        localRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_localvideo);
        manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        localRecyclerView.setLayoutManager(manager);

        adapter = new LocalVideoAdapter(context);
        localRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        loadDataFromLocal();
    }


    /**
     * 从本地加载数据
     */
    private void loadDataFromLocal() {
        videoList = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                super.run();
//                isGrantExternalRW((Activity) context);
                ContentResolver resolver = context.getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String[] obj = {
                        MediaStore.Video.Media.DISPLAY_NAME,   //视频文件在sdcard中名称
                        MediaStore.Video.Media.DURATION,    //视频总长度
                        MediaStore.Video.Media.SIZE,    //视频文件大小
                        MediaStore.Video.Media.DATA,    //视频绝对地址
                        MediaStore.Video.Media.ARTIST,   //歌曲的演唱者
                        MediaStore.Video.Media.ALBUM,     //专辑
                        MediaStore.Video.Media.DESCRIPTION,  //描述
                        MediaStore.Video.Media.BOOKMARK,   //bookmark
                        MediaStore.Video.Media.BUCKET_DISPLAY_NAME,  //bucket_display_name
                        MediaStore.Video.Media.CATEGORY,   //category
                        MediaStore.Video.Media.TITLE    // title
                };
                Cursor cursor = resolver.query(uri, obj, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        LocalVideoBean localVideoBean = new LocalVideoBean();
                        localVideoBean.setName(cursor.getString(0));
                        localVideoBean.setTimeLength(cursor.getLong(1));
                        localVideoBean.setCapcity(cursor.getLong(2));
                        localVideoBean.setPath(cursor.getString(3));
                        localVideoBean.setArtist(cursor.getString(4));
                        localVideoBean.setAlbum(cursor.getString(5));
                        localVideoBean.setDescription(cursor.getString(6));
                        localVideoBean.setBookMark(cursor.getString(7));
                        localVideoBean.setBucketDisplayName(cursor.getString(8));
                        localVideoBean.setCategory(cursor.getString(9));
                        localVideoBean.setTitle(cursor.getString(10));
                        if (0 == sharedPreferences.getLong(localVideoBean.getName(), 0)) {
                            //share中不存在该视频播放记录，并且在上一次扫描中没有该视频
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putLong(localVideoBean.getName(), 1);
                            localVideoBean.setTimeSeenLength(0);
                        } else if (1 == sharedPreferences.getLong(localVideoBean.getName(), 0)) {
                            //share中不存在该视频播放记录，但是在上一次扫描中有该视频
                            localVideoBean.setTimeSeenLength(0);
                        } else {
                            //该视频有播放记录
                            localVideoBean.setTimeSeenLength(sharedPreferences.getLong(localVideoBean.getName(), 0));
                        }
                        videoList.add(localVideoBean);
                    }
                    cursor.close();
                }
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    //动态获取读取外部存储的权限
    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

            return false;
        }

        return true;
    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(context, PlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("VideoList", (Serializable) videoList);
        bundle.putInt("currentPosition", position);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
