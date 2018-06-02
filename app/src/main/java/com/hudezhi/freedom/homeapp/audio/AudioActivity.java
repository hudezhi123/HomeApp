package com.hudezhi.freedom.homeapp.audio;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.audio.adapter.AudioWelcomePagerAdapter;
import com.hudezhi.freedom.homeapp.audio.service.PlayMusicService;
import com.hudezhi.freedom.homeapp.audio.utils.MediaUtils;

import java.util.ArrayList;
import java.util.List;

public class AudioActivity extends AppCompatActivity {
    private static final int START_ACTIVITY_FLAG = 1;
    private ViewPager viewPager;
    private LinearLayout linearBottomDot;
    private AudioWelcomePagerAdapter adapter;
    private List<View> viewList;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_ACTIVITY_FLAG:
                    Intent intent = new Intent(AudioActivity.this, AudioMainActivity.class);
                    startActivity(intent);
                    AudioActivity.this.finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        startService(new Intent(this, PlayMusicService.class));
        initView();
        initDBData();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                handler.sendEmptyMessage(START_ACTIVITY_FLAG);
            }
        }, 3000);
    }

    private void initDBData() {
        MediaUtils.insertMusicInfoIToDB();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager_audio_welcome);
        linearBottomDot = (LinearLayout) findViewById(R.id.linear_bottom_dot);

    }

    private void initData() {
        viewList = new ArrayList<>();
        for (int i = 0; i < linearBottomDot.getChildCount(); i++) {
        }

    }

    private void init() {

    }
}
