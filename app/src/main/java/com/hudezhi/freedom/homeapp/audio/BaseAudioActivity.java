package com.hudezhi.freedom.homeapp.audio;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.audio.activity.MusicPlayingActivity;
import com.hudezhi.freedom.homeapp.audio.bean.MusicInfo;
import com.hudezhi.freedom.homeapp.audio.service.PlayMusicService;
import com.hudezhi.freedom.homeapp.audio.utils.MediaUtils;
import com.hudezhi.freedom.homeapp.audio.utils.MusicDBHelper;


/**
 * Created by boy on 2017/4/9.
 */

public abstract class BaseAudioActivity extends FragmentActivity {

    public static final int REQUEST_CODE_SONGID = 101;
    public static final int RESULT_CODE_SONGID = 102;

    private LinearLayout linearProgressBar;

    //titleBar
    private TextView logoName;
    private ImageView imgLogo;

    //BottomBar
    private RelativeLayout bottomBar;
    private ImageView imgAlbum;
    private TextView textSongName;
    private TextView textArtist;
    private ImageView imgFocus;
    public ImageView imgPlaySong;
    private ImageView imgGoFast;

    private boolean isFocus = false;

    private RelativeLayout parentRelativeLayout;

    private WindowManager mWindowManager;
    private View cloneView;
    private WindowManager.LayoutParams mParams;

    private Animation upAnimation;

    private final static int PLAY_FALL_DOWN_AMIN = 1;
    private static int iteration = 0;

    private boolean isBound = false;

    public PlayMusicService playMusicService;

    private MusicDBHelper dbHelper = null;

    private PlayMusicService.MusicUpdateListener musicUpdateListener = new PlayMusicService.MusicUpdateListener() {

        @Override
        public void onChange(int songId) {
            // TODO: 2017/6/6
            changeUI(songId);
        }
    };

    private PlayMusicService.MusicStateChangedListener musicStateChangedListener = new PlayMusicService.MusicStateChangedListener() {
        @Override
        public void playState(boolean isPlay) {
            if (isPlay) {
                imgPlaySong.setImageResource(R.mipmap.pause_audio_icon);
            } else {
                imgPlaySong.setImageResource(R.mipmap.play_audio_icon);
            }
        }
    };


    //    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case PLAY_FALL_DOWN_AMIN:
//                    if (mParams.y <= MAX_Y) {
//                        mWindowManager.removeView(cloneView);
//                        removeMessages(PLAY_FALL_DOWN_AMIN);
//                        iteration = 0;
//                        return;
//                    } else {
//                        mWindowManager.updateViewLayout(cloneView, mParams);
//                        mParams.y -= iteration++;
//                        sendEmptyMessageDelayed(PLAY_FALL_DOWN_AMIN, 20);
//                    }
//                    break;
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new MusicDBHelper(getApplicationContext());
        initContentView(R.layout.activity_audio_base);
        bindPlayerService();
        initBottomBar();
        Log.i("oncreate", "oncreate");
//        initCloneWindow();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private long recentSongId = -1;

    private void getData() {
        if (PlayMusicService.isPlay) {

        }
        MusicInfo musicInfo = MediaUtils.getRecentItemFromDB();
        if (musicInfo != null) {
            recentSongId = musicInfo.id;
            Bitmap bitmap = MediaUtils.getThumbnail(musicInfo.albumIMGUri);
            imgAlbum.setImageBitmap(bitmap);
            textArtist.setText(musicInfo.artistName);
            textSongName.setText(musicInfo.songName);
            if (musicInfo.isFocus) {
                imgFocus.setImageResource(R.mipmap.focus_icon_red);
            } else {
                imgFocus.setImageResource(R.mipmap.focus_icon_gray);
            }
            if (playMusicService != null) {
                if (playMusicService.isPlaying()) {
                    imgPlaySong.setImageResource(R.mipmap.pause_audio_icon);
                } else {
                    imgPlaySong.setImageResource(R.mipmap.play_audio_icon);
                }
            } else {
                if (PlayMusicService.isPlay) {
                    imgPlaySong.setImageResource(R.mipmap.pause_audio_icon);
                } else {
                    imgPlaySong.setImageResource(R.mipmap.play_audio_icon);
                }
            }
        } else {
            return;
        }


    }

    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.hudezhi.freedom.homeapp.audio.service.PlayMusicService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    //绑定服务
    public void bindPlayerService() {
        if (!isBound) {
            Intent intent = new Intent(this, PlayMusicService.class);
            bindService(intent, conn, Context.BIND_AUTO_CREATE);
            isBound = true;
        }
    }

    //接触绑定服务
    public void unBindPlayerService() {
        if (isBound) {
            unbindService(conn);
            isBound = false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindPlayerService();
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayMusicService.PlayBinder playBinder = (PlayMusicService.PlayBinder) service;
            playMusicService = playBinder.getPlayMusicService();
            playMusicService.setMusicUpdateListener(musicUpdateListener);
            playMusicService.setMusicStateChanged(musicStateChangedListener);
//            playMusicService.setMusicUpdateProgressListener();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (playMusicService != null) {
                playMusicService = null;
            }

        }
    };

    public void initTitleBar() {
        logoName = (TextView) findViewById(R.id.text_logo_name);
        imgLogo = (ImageView) findViewById(R.id.img_logo_audio);
    }

    /**
     * 初始化cloneView
     */
//    private void initCloneWindow() {
//        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        cloneView = LayoutInflater.from(this).inflate(R.layout.clone_img_layout, null);
//
//        upAnimation = AnimationUtils.loadAnimation(BaseAudioActivity.this, R.anim.audio_focus_anim);
//        upAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//    }

    //初始化contentView
    private void initContentView(int layoutResID) {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        parentRelativeLayout = new RelativeLayout(this);
        viewGroup.addView(parentRelativeLayout);
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        parentRelativeLayout.addView(view, lp);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.ABOVE, parentRelativeLayout.getChildAt(0).getId());
        parentRelativeLayout.addView(view, lp);
    }

    @Override
    public void setContentView(View view) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.ABOVE, parentRelativeLayout.getChildAt(0).getId());
        parentRelativeLayout.addView(view, lp);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        parentRelativeLayout.addView(view, params);
    }

    /**
     * 底部控制器
     */
    public void initBottomBar() {
        bottomBar = (RelativeLayout) findViewById(R.id.relative_bottom_audio_play);
        imgAlbum = (ImageView) findViewById(R.id.img_music_icon_audio_base);
        textSongName = (TextView) findViewById(R.id.text_music_name_audio_base);
        textArtist = (TextView) findViewById(R.id.text_artist_name_audio_base);
        imgFocus = (ImageView) findViewById(R.id.img_focus_audio_base);
        imgPlaySong = (ImageView) findViewById(R.id.img_play_audio_base);
        imgGoFast = (ImageView) findViewById(R.id.img_gofast_audio_base);
        bottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseAudioActivity.this, MusicPlayingActivity.class);
                long songId = playMusicService.getCurrentSongId();
                Bundle bundle = new Bundle();
                bundle.putLong(MusicDBHelper.SONG_ID, songId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        imgFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isFocus = !isFocus;
                if (isFocus) {
                    imgFocus.setImageResource(R.mipmap.focus_icon_red);
                } else {
                    imgFocus.setImageResource(R.mipmap.focus_icon_gray);
                }
                long currentSongid = playMusicService.getCurrentSongId();
                String sql_update = "update " + MusicDBHelper.TB_MY_MUSIC_INFO + " set "
                        + MusicDBHelper.IS_FOCUS
                        + " ='"
                        + String.valueOf(isFocus) + "' where "
                        + MusicDBHelper.SONG_ID
                        + "='" + currentSongid +
                        "';";
                dbHelper.execSQL(sql_update);
                dbHelper.close();
                MusicInfo mu = MediaUtils.getChooseItemFromDB((int) currentSongid);
            }
        });
        imgPlaySong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isPlaying = playMusicService.isPlaying();
                if (isPlaying) {
                    playMusicService.pausePlay();
                    imgPlaySong.setImageResource(R.mipmap.play_audio_icon);
                } else {
                    playMusicService.start();
                    if (!playMusicService.isPlaying()) {
                        playMusicService.playCurrentPosition(MediaUtils.hashSearch.get(recentSongId));
                    }
                    imgPlaySong.setImageResource(R.mipmap.pause_audio_icon);
                }
            }
        });
        imgGoFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusicService.nextPlay();
                boolean isPlaying = playMusicService.isPlaying();
                if (isPlaying) {
                    imgPlaySong.setImageResource(R.mipmap.pause_audio_icon);
                }
            }
        });
    }

    public void changeUI(int songId) {
        MusicInfo musicInfo = MediaUtils.getChooseItemFromDB(songId);
        Bitmap bitmap = MediaUtils.getThumbnail(musicInfo.albumIMGUri);
        imgAlbum.setImageBitmap(bitmap);
        textArtist.setText(musicInfo.artistName);
        textSongName.setText(musicInfo.songName);
        if (musicInfo.isFocus) {
            imgFocus.setImageResource(R.mipmap.focus_icon_red);
        } else {
            imgFocus.setImageResource(R.mipmap.focus_icon_gray);
        }
        if (playMusicService != null && playMusicService.isPlaying()) {
            imgPlaySong.setImageResource(R.mipmap.pause_audio_icon);
        } else {
            imgPlaySong.setImageResource(R.mipmap.play_audio_icon);
        }
    }

    //初始化进度条
    public void initPorgres() {
        linearProgressBar = (LinearLayout) findViewById(R.id.linear_progress_for_data);
    }

    //加载数据，显示进度条，圆形
    public void showProgress() {
        if (linearProgressBar != null && linearProgressBar.getVisibility() != View.VISIBLE) {
            linearProgressBar.setVisibility(View.VISIBLE);
        }
    }

    //数据加载完毕，隐藏进度条
    public void removeProgress() {
        if (linearProgressBar != null && linearProgressBar.getVisibility() == View.VISIBLE) {
            linearProgressBar.setVisibility(View.GONE);
        }
    }

//    private int MAX_Y;

    //    private WindowManager.LayoutParams getDefaultWindowParams() {
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                0, 0,
//                WindowManager.LayoutParams.TYPE_TOAST,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                PixelFormat.RGBA_8888);
//
//        //计算ImageView 在屏幕中的位置
//        int[] location = new int[2];
//        imgFocus.getLocationInWindow(location);
//        int startX = location[0];
//        int startY = location[1];
//        params.x = startX;
//        params.y = startY;
//        MAX_Y = startY - 2 * imgFocus.getMeasuredHeight();
//        return params;
//    }


    public class ChangeUIBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
