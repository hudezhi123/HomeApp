package com.hudezhi.freedom.homeapp.video.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.audio.AudioActivity;
import com.hudezhi.freedom.homeapp.video.bean.LocalVideoBean;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PlayerActivity extends Activity implements View.OnClickListener {
    private GestureDetector gestureDetector;
    private View include;
    private TextView title, systemTime, passTime, totalTime;
    private ImageView power, backActivity, preVideoItem, nextVideoItem, light, voice;
    private CheckBox pauseOrPlay, screen;
    private SeekBar videoProgress;
    private VideoView mplayer;
    private List<LocalVideoBean> videoList;
    private int currentPositon;
    private PowerStateReciever powerStateReciever;
    private boolean isPlaying, isPreBtnClickble, isNextBtnClickble;
    private static final int SEEKBAR_PROGRESS = 1, TIME_UPDATE = 2;
    private int screenWidth, screenHeight;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TIME_UPDATE:
                    String time = getSystemTime();
                    systemTime.setText(time);
                    handler.removeMessages(TIME_UPDATE);
                    handler.sendEmptyMessageDelayed(TIME_UPDATE, 1000);
                    break;
                case SEEKBAR_PROGRESS:
                    int pass = mplayer.getCurrentPosition();
                    videoProgress.setProgress(pass);
                    passTime.setText(formatTime(pass));
                    handler.removeMessages(SEEKBAR_PROGRESS);
                    handler.sendEmptyMessageDelayed(SEEKBAR_PROGRESS, 1000);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //设置为全屏，在加载内容之前
        setContentView(R.layout.activity_player);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getIntentData();
        initView();
        initData();
        setListener();
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        if (powerStateReciever != null) {
            unregisterReceiver(powerStateReciever);
            powerStateReciever = null;
        }
        super.onDestroy();
    }

    /**
     * 初始化View
     */
    private void initView() {
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        mplayer = (VideoView) findViewById(R.id.videoView);
        include = findViewById(R.id.self_media_controller);
        title = (TextView) findViewById(R.id.video_title);
        systemTime = (TextView) findViewById(R.id.system_time);
        totalTime = (TextView) findViewById(R.id.total_time);
        passTime = (TextView) findViewById(R.id.pass_time);
        power = (ImageView) findViewById(R.id.cell_power);

        backActivity = (ImageView) findViewById(R.id.close_current_player);
        preVideoItem = (ImageView) findViewById(R.id.pre_video_item);
        nextVideoItem = (ImageView) findViewById(R.id.next_video_item);
        if (currentPositon == 0) {
            preVideoItem.setClickable(false);
            preVideoItem.setImageResource(R.mipmap.pre_unclickble);
            isPreBtnClickble = false;
        }
        if (currentPositon == videoList.size() - 1) {
            nextVideoItem.setClickable(false);
            nextVideoItem.setImageResource(R.mipmap.next_unclickble);
            isNextBtnClickble = false;
        }
        light = (ImageView) findViewById(R.id.adjust_light);
        voice = (ImageView) findViewById(R.id.adjust_voice);
        screen = (CheckBox) findViewById(R.id.change_screen_orientation);
        pauseOrPlay = (CheckBox) findViewById(R.id.pause_play);
        videoProgress = (SeekBar) findViewById(R.id.progress_adjust);
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                changeScreenOrientation();
                return super.onDoubleTap(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                if (mplayer.isPlaying()) {
                    pauseOrPlay.setChecked(true);
                } else {
                    pauseOrPlay.setChecked(false);
                }

            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (include.getVisibility() == View.VISIBLE) {
                    include.setVisibility(View.GONE);
                } else if (include.getVisibility() == View.GONE) {
                    include.setVisibility(View.VISIBLE);
                }
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                // e1：第1个ACTION_DOWN MotionEvent
                // e2：最后一个ACTION_MOVE MotionEvent
                // velocityX：X轴上的移动速度（像素/秒）
                // velocityY：Y轴上的移动速度（像素/秒）

                float startX = e1.getRawX();
                float startY = e1.getRawY();
                float endX = e2.getRawX();
                float endY = e2.getRawY();
                if (startX > screenWidth / 2) {
                    //音量调节
                    float distanceVoice = startY - endY;
                    adjustVolume(distanceVoice);
                } else if (startX <= screenWidth / 2) {
                    //亮度调节
                    float distanceBright = endY - startY;

                }
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });
    }

    private void setPauseOrPlay() {
        if (mplayer.isPlaying()) {
            mplayer.pause();
            pauseOrPlay.setChecked(true);
        } else {
            mplayer.start();
            pauseOrPlay.setChecked(false);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        backActivity.setOnClickListener(this);
        preVideoItem.setOnClickListener(this);
        nextVideoItem.setOnClickListener(this);
        light.setOnClickListener(this);
        voice.setOnClickListener(this);
        videoProgress.setOnSeekBarChangeListener(new MySeekBarChangeListener());
        pauseOrPlay.setOnCheckedChangeListener(new MyCheckChangeListener());
        screen.setOnCheckedChangeListener(new MyCheckChangeListener());
        mplayer.setOnClickListener(this);
        mplayer.setOnPreparedListener(new OnPreparedListener());
        mplayer.setOnErrorListener(new OnErrorListener());
        mplayer.setOnCompletionListener(new OnCompletionListener());
    }

    /**
     * 得到从跳转页面获得的数据
     */
    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        videoList = (List<LocalVideoBean>) bundle.getSerializable("VideoList");
        currentPositon = bundle.getInt("currentPosition");
    }

    private void initData() {
        //注册电量广播
        powerStateReciever = new PowerStateReciever();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(powerStateReciever, filter);
        if (videoList != null && videoList.size() > 0) {
            LocalVideoBean localVideoBean = videoList.get(currentPositon);
            title.setText(localVideoBean.getName());
            totalTime.setText(formatTime(localVideoBean.getTimeLength()));
            passTime.setText(formatTime(0));
            mplayer.setVideoPath(localVideoBean.getPath());
            handler.sendEmptyMessage(TIME_UPDATE);
        }
    }

    class OnPreparedListener implements MediaPlayer.OnPreparedListener {

        //当底层解码准备好的时候
        @Override
        public void onPrepared(MediaPlayer mp) {
            mplayer.start();
            pauseOrPlay.setChecked(false);
            videoProgress.setMax(mplayer.getDuration());
            handler.sendEmptyMessage(SEEKBAR_PROGRESS);
        }
    }

    class OnErrorListener implements MediaPlayer.OnErrorListener {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            Toast.makeText(PlayerActivity.this, "视频播放出错", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    class OnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            if (!pauseOrPlay.isChecked()) {
                pauseOrPlay.setChecked(true);
            }
        }
    }

    /**
     * 将时间格式化为00:00:00
     *
     * @param time
     * @return
     */
    private String formatTime(long time) {
        time /= 1000;
        int seconde = (int) (time % 60);
        int minute = (int) (time / 60 % 60);
        int hour = (int) (time / 60 / 60 % 12);
        return formatMM(hour) + ":" + formatMM(minute) + ":" + formatMM(seconde);
    }

    private String formatMM(int data) {
        if (data >= 10) {
            return data + "";
        }
        return "0" + data;
    }

    /**
     * 自定义seekbar的监听器
     */
    public class MySeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mplayer.seekTo(progress);
                passTime.setText(formatTime(progress));
                mplayer.start();
                if (mplayer.isPlaying()) {
                    pauseOrPlay.setChecked(false);
                    handler.sendEmptyMessage(SEEKBAR_PROGRESS);
                } else {
                    pauseOrPlay.setChecked(true);
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    /**
     * 播放状态的监听和屏幕切换的监听
     */
    public class MyCheckChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.pause_play:
                    setPauseOrPlay();
                    break;
                case R.id.change_screen_orientation:
                    if (buttonView.isChecked()) {
                        //设置为全屏
//                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        //设置为横屏
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    } else {
//                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.videoView:

                break;
            case R.id.close_current_player:

                break;
            case R.id.pre_video_item:
                if (currentPositon - 1 <= 0) {
                    preVideoItem.setClickable(false);
                    preVideoItem.setImageResource(R.mipmap.pre_unclickble);
                    isPreBtnClickble = false;

                } else {
                    preVideoItem.setClickable(true);
                    preVideoItem.setImageResource(R.mipmap.ic_pre_item);
                    isPreBtnClickble = true;
                }
                if (!isNextBtnClickble) {
                    isNextBtnClickble = true;
                    nextVideoItem.setImageResource(R.mipmap.ic_next_item);
                    nextVideoItem.setClickable(true);
                }
                currentPositon--;
                setPreVideoItem(currentPositon);
                break;
            case R.id.next_video_item:
                if (currentPositon + 1 >= videoList.size() - 1) {
                    nextVideoItem.setClickable(false);
                    nextVideoItem.setImageResource(R.mipmap.next_unclickble);
                    isNextBtnClickble = false;

                } else {
                    nextVideoItem.setClickable(true);
                    nextVideoItem.setImageResource(R.mipmap.ic_next_item);
                    isNextBtnClickble = true;
                }
                if (!isPreBtnClickble) {
                    isPreBtnClickble = true;
                    preVideoItem.setImageResource(R.mipmap.ic_pre_item);
                    preVideoItem.setClickable(true);
                }
                currentPositon++;
                setNextVideoItem(currentPositon);
                break;
            case R.id.adjust_light:

                break;
            case R.id.adjust_voice:

                break;
        }
    }


    class PowerStateReciever extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", 0);
            setBatterLevelState(level);
        }
    }

    /**
     * 得到手机电量情况
     */
    private void setBatterLevelState(int level) {
        if (level <= 90 && level > 70) {
            power.setImageResource(R.mipmap.ic_eight_power);
        } else if (level > 45 && level <= 70) {
            power.setImageResource(R.mipmap.ic_six_power);
        } else if (level > 90 && level <= 100) {
            power.setImageResource(R.mipmap.ic_full_power);
        } else if (level > 25 && level <= 45) {
            power.setImageResource(R.mipmap.ic_four_power);
        } else if (level > 10 && level <= 25) {
            power.setImageResource(R.mipmap.ic_two_power);
        } else {
            power.setImageResource(R.mipmap.ic_null_power);
        }
    }

    /**
     * 得到系统时间
     */
    private String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 根据得到的videoPath，来决定播放的video
     */
    private String setVideoPlayPath(int position) {
        LocalVideoBean localVideoBean = videoList.get(position);
        title.setText(localVideoBean.getName());
        totalTime.setText(formatTime(localVideoBean.getTimeLength()));
        return localVideoBean.getPath();
    }

    /**
     * 下一个video
     */
    private void setNextVideoItem(int position) {
        if (videoList != null && videoList.size() > 0 && position < videoList.size()) {
            String playPath = setVideoPlayPath(currentPositon);
            if (!playPath.isEmpty()) {
                mplayer.setVideoPath(playPath);
            }
        }
    }

    /**
     * 上一个video
     */
    private void setPreVideoItem(int position) {
        if (videoList != null && videoList.size() > 0 && position >= 0) {
            String playPath = setVideoPlayPath(position);
            if (!playPath.isEmpty()) {
                mplayer.setVideoPath(playPath);
            }
        }
    }

    /**
     * 调节亮度
     */
    private void adjustLight(float distanceY) {

    }

    /**
     * 调节音量
     */
    private void adjustVolume(float distanceY) {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        float currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float changeVolume = distanceY * maxVolume / screenWidth / 10;
        float resultVolume = 0;
        if (distanceY >= 0) {
            resultVolume = Math.min(changeVolume + currentVolume, maxVolume);
        } else {
            resultVolume = Math.max(changeVolume + currentVolume, 0);
        }
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) resultVolume, AudioManager.FLAG_SHOW_UI);
    }

    /**
     * 横竖屏切换
     */
    private void changeScreenOrientation() {
        if (screen.isChecked()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            screen.setChecked(false);
        } else {
            //设置为全屏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //设置为横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            screen.setChecked(true);
        }
    }


}
