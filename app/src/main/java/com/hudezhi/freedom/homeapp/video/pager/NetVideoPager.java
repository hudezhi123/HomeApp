package com.hudezhi.freedom.homeapp.video.pager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.video.activity.DownloadActivity;
import com.hudezhi.freedom.homeapp.video.bean.FileUrl;
import com.hudezhi.freedom.homeapp.video.bean.VideoDBHelper;
import com.hudezhi.freedom.homeapp.video.videobase.BasePager;

import java.util.List;

/**
 * Created by hudezhi on 2017/3/12.
 * 网络视频页面
 */

public class NetVideoPager extends BasePager {

    private ListView listview;

    public NetVideoPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.net_video_layout, null);
        listview = (ListView) view.findViewById(R.id.listview_net_video);
        final List<String> urlList = FileUrl.getUrlList(FileUrl.URL_TYPE_VIDEO);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_expandable_list_item_1, urlList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String fileName = urlList.get(position);
//                VideoDBHelper dbHelper = new VideoDBHelper(context);
//                String sql = "update "
//                        + VideoDBHelper.VIDEO_PROCESS_TB
//                        + " set " + VideoDBHelper.FILE_NAME
//                        + "='" + fileName + "' where "+VideoDBHelper.FILE_NAME+";" ;
//                dbHelper.execSQL(sql);
                Intent intent = new Intent(context, DownloadActivity.class);
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void initData() {

        super.initData();

    }
}
