package com.hudezhi.freedom.homeapp.audio.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hudezhi.freedom.homeapp.R;
import com.hudezhi.freedom.homeapp.audio.adapter.PlayingFragmentPagerAdapter;
import com.hudezhi.freedom.homeapp.audio.bean.MusicInfo;
import com.hudezhi.freedom.homeapp.audio.fragment.PlayingImgFragment;
import com.hudezhi.freedom.homeapp.audio.fragment.PlayingLyricFragment;
import com.hudezhi.freedom.homeapp.audio.fragment.PlayingOtherAlbumFragment;
import com.hudezhi.freedom.homeapp.audio.service.PlayMusicService;
import com.hudezhi.freedom.homeapp.audio.utils.MediaUtils;
import com.hudezhi.freedom.homeapp.audio.utils.MusicDBHelper;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayingActivity extends FragmentActivity implements View.OnClickListener {
    private PlayMusicService playMusicService;
    private ViewPager viewPager;
    private LinearLayout linearDots;
    private List<Fragment> fragmentList;
    private PlayingImgFragment playingImgFragment;
    private PlayingLyricFragment playingLyricFragment;
    private PlayingOtherAlbumFragment playingOtherAlbumFragment;
    private PlayingFragmentPagerAdapter adapter;
    private FrameLayout frameImgDetail;
    private boolean isMove = false;
    private boolean isBound = false;
    private static int mState = 0;

    private ImageView imgDownFinish;
    private ImageView imgCriticize;
    private ImageView imgAlbumIcon;
    private ImageView imgPreMusicItem;
    private ImageView imgPlayMusic;
    private ImageView imgNextMusicItem;
    private ImageView imgMusicList;
    private ImageView imgPlayMusicState;
    private ImageView imgDownLoad;
    private ImageView imgFocus;
    private ImageView imgMore;

    private TextView textSongName;
    private TextView textAlbumTitle;
    private TextView textCriticizeCount;
    private TextView textPassTime;
    private TextView textDuration;

    private SeekBar seekBar;

    private static final int UPDATE = 1;
    private static final int NO_UPDATE = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE:
                    int progress = (int) msg.obj;
                    String time = formatTime(progress);
                    textPassTime.setText(time + "");
                    break;
                case NO_UPDATE:
                    break;
            }

        }
    };


    private PlayMusicService.MusicUpdateProgressListener updateProgressListener = new PlayMusicService.MusicUpdateProgressListener() {
        @Override
        public void onUpdateProgress(int progress) {
            Message msg = handler.obtainMessage();
            if (playMusicService != null) {
                if (playMusicService.isPlaying()) {
                    seekBar.setProgress(progress);
                    msg.obj = progress;
                    msg.what = UPDATE;
                    handler.sendMessage(msg);
                } else {
                    handler.sendEmptyMessage(NO_UPDATE);
                }
            } else {
                seekBar.setProgress(progress);
                handler.sendEmptyMessage(progress);
            }
        }
    };

    private PlayMusicService.MusicPositionChangeListener musicPositionChangeListener = new PlayMusicService.MusicPositionChangeListener() {
        @Override
        public void positionChange(int position) {
            changeUI();
        }
    };

    private void initView() {
        frameImgDetail = (FrameLayout) findViewById(R.id.frame_music_img_view);
        viewPager = (ViewPager) findViewById(R.id.viewpager_music_playing_activity);
        linearDots = (LinearLayout) findViewById(R.id.linear_dot_music_playing);
        imgDownFinish = (ImageView) findViewById(R.id.img_down_arrow_finish_playing_titlebar);
        imgCriticize = (ImageView) findViewById(R.id.img_criticize_playing_titlebar);
        imgAlbumIcon = (ImageView) findViewById(R.id.img_album_img_music_playing_activity);
        imgPreMusicItem = (ImageView) findViewById(R.id.img_pre_item_music_playing_activity);
        imgPlayMusic = (ImageView) findViewById(R.id.img_play_music_playing_activity);
        imgNextMusicItem = (ImageView) findViewById(R.id.img_next_music_playing_activity);
        imgMusicList = (ImageView) findViewById(R.id.img_musiclist_music_playing_activity);
        imgPlayMusicState = (ImageView) findViewById(R.id.img_playstate_music_playing_activity);
        imgDownLoad = (ImageView) findViewById(R.id.img_download_music_playing_activity);
        imgFocus = (ImageView) findViewById(R.id.img_focus_music_playing_activity);
        imgMore = (ImageView) findViewById(R.id.img_more_music_playing_activity);
        textSongName = (TextView) findViewById(R.id.text_song_name_playing_titlebar);
        textAlbumTitle = (TextView) findViewById(R.id.text_singer_title_playing_titlebar);
        textCriticizeCount = (TextView) findViewById(R.id.text_criticize_count_playing_titlebar);
        textPassTime = (TextView) findViewById(R.id.text_passtime_music_playing_activity);
        textDuration = (TextView) findViewById(R.id.text_duration_music_playing_activity);
        seekBar = (SeekBar) findViewById(R.id.seekbar_music_playing_activity);
        imgDownFinish.setOnClickListener(this);
        imgCriticize.setOnClickListener(this);
        imgPreMusicItem.setOnClickListener(this);
        imgPlayMusic.setOnClickListener(this);
        imgNextMusicItem.setOnClickListener(this);
        imgMusicList.setOnClickListener(this);
        imgPlayMusicState.setOnClickListener(this);
        imgDownLoad.setOnClickListener(this);
        imgFocus.setOnClickListener(this);
        imgMore.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    seekBar.setProgress(progress);
                    playMusicService.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayMusicService.PlayBinder playBinder = (PlayMusicService.PlayBinder) service;
            playMusicService = playBinder.getPlayMusicService();
            playMusicService.setMusicUpdateProgressListener(updateProgressListener);
            playMusicService.setMusicPositionChangeListener(musicPositionChangeListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (playMusicService != null) {
                playMusicService = null;
            }
        }
    };


    private void bindMusicService() {
        Intent intent = new Intent(this, PlayMusicService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    //解除绑定服务
    public void unBindPlayerService() {
        if (isBound) {
            unbindService(conn);
            isBound = false;
        }

    }

    //    private
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playing);
        Log.i("oncreate", "oncreate");
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        init();
    }


    private void init() {
        initView();
        initFragmentList();
        adapter = new PlayingFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        isMove = true;
                        break;
                }
                return false;
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int num = 0;
            private float start = -1;
            private int startCrrent = -1;
            private int startPosition = -1;
            private int state;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (isMove) {
                    if (startCrrent < 0) {
                        startCrrent = viewPager.getCurrentItem();
                    }
                    if (startPosition < 0) {
                        startPosition = position;
                        frameImgDetail.setVisibility(View.VISIBLE);
                    }
                    if (startPosition == 0 && startCrrent == 0) {
                        frameImgDetail.setAlpha(positionOffset);
                        if (positionOffset == 0 && viewPager.getCurrentItem() == 1) {
                            frameImgDetail.setAlpha(1);
                        } else if (positionOffset == 0 && viewPager.getCurrentItem() == 0) {
                            frameImgDetail.setVisibility(View.GONE);
                        }
                    } else if (startPosition == 1 && startCrrent == 1) {
                        frameImgDetail.setAlpha(1 - positionOffset);
                        if (positionOffset == 0 && viewPager.getCurrentItem() == 2) {
                            frameImgDetail.setVisibility(View.GONE);
                        } else if (positionOffset == 0 && viewPager.getCurrentItem() == 1) {
                            frameImgDetail.setVisibility(View.VISIBLE);
                        }
                    } else if (startPosition == 1 && startCrrent == 2) {
                        frameImgDetail.setAlpha(1 - positionOffset);
                        if (positionOffset == 0 && viewPager.getCurrentItem() == 1) {
                            frameImgDetail.setVisibility(View.VISIBLE);
                        } else if (positionOffset == 0 && viewPager.getCurrentItem() == 2) {
                            frameImgDetail.setVisibility(View.GONE);
                        }
                    } else if (startPosition == 0 && startCrrent == 1) {
                        frameImgDetail.setAlpha(positionOffset);
                        if (positionOffset == 0 && viewPager.getCurrentItem() == 0) {
                            frameImgDetail.setVisibility(View.GONE);
                        } else if (positionOffset == 0 && viewPager.getCurrentItem() == 1) {
                            frameImgDetail.setVisibility(View.VISIBLE);
                            frameImgDetail.setAlpha(1);
                        }
                    } else if (startPosition == 2 && startCrrent == 2) {
                        frameImgDetail.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

                int count = linearDots.getChildCount();
                for (int i = 0; i < count; i++) {
                    ImageView imgDot = (ImageView) linearDots.getChildAt(i);
                    imgDot.setImageResource(R.mipmap.dot_unselected);
                }
                ImageView imgDot = (ImageView) linearDots.getChildAt(position);
                imgDot.setImageResource(R.mipmap.dot_selected);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                this.state = state;
                if (ViewPager.SCROLL_STATE_IDLE != state) {
                    isMove = true;
                } else {
                    isMove = false;
                    startCrrent = -1;
                    startPosition = -1;
                    if (viewPager.getCurrentItem() == 0 || viewPager.getCurrentItem() == 2) {
                        frameImgDetail.setVisibility(View.GONE);
                    }
                }
            }
        });
        viewPager.setCurrentItem(1);
    }

    private void initFragmentList() {
        fragmentList = new ArrayList<>();
        playingImgFragment = new PlayingImgFragment();
        playingOtherAlbumFragment = new PlayingOtherAlbumFragment();
        playingLyricFragment = new PlayingLyricFragment();
        fragmentList.add(playingOtherAlbumFragment);
        fragmentList.add(playingImgFragment);
        fragmentList.add(playingLyricFragment);
    }

    private long songId;

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        songId = bundle.getLong(MusicDBHelper.SONG_ID);
        if(songId==-1){
            return;
        }
        MusicInfo musicInfo = MediaUtils.getChooseItemFromDB((int) songId);
        textSongName.setText(musicInfo.songName);
        textAlbumTitle.setText(musicInfo.artistName + " - " + musicInfo.songName);
        textDuration.setText(formatTime(musicInfo.duration));
        if (PlayMusicService.isPlay) {
            imgPlayMusic.setImageResource(R.mipmap.audio_pause_big_icon);
        } else {
            imgPlayMusic.setImageResource(R.mipmap.audio_play_big_icon);
        }
        Bitmap bitmap = BitmapFactory.decodeFile(musicInfo.albumIMGUri);
        if (bitmap != null) {
            imgAlbumIcon.setImageBitmap(bitmap);
        }
        if (playMusicService != null && playMusicService.isPlaying()) {
            textPassTime.setText(formatTime(playMusicService.getCurrentProgress()));
        } else {
            textPassTime.setText(formatTime(0));
        }
        seekBar.setMax((int) musicInfo.duration);
    }

    /**
     * @param time
     * @return
     */
    private String formatTime(long time) {
        time /= 1000;
        int seconde = (int) (time % 60);
        int minute = (int) (time / 60 % 60);
        int hour = (int) (time / 60 / 60 % 12);
        if (hour == 0) {
            return formatMM(minute) + ":" + formatMM(seconde);
        } else {
            return formatMM(hour) + ":" + formatMM(minute) + ":" + formatMM(seconde);
        }

    }

    private String formatMM(int data) {
        if (data >= 10) {
            return data + "";
        }
        return "0" + data;
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindMusicService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unBindPlayerService();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_down_arrow_finish_playing_titlebar:
                finish();
                break;
            case R.id.img_criticize_playing_titlebar:
                Intent intent = new Intent(this, CriticizeActivity.class);
                startActivity(intent);
                break;
            case R.id.img_pre_item_music_playing_activity:
                playMusicService.prePlay();
                changeUI();
                break;
            case R.id.img_play_music_playing_activity:
                boolean isPlaying = playMusicService.isPlaying();
                if (isPlaying) {
                    imgPlayMusic.setImageResource(R.mipmap.audio_play_big_icon);
                    playMusicService.pausePlay();
                } else {
                    playMusicService.start();
                    if (!playMusicService.isPlaying()) {
                        playMusicService.playCurrentPosition(MediaUtils.hashSearch.get(songId));
                    }
                    imgPlayMusic.setImageResource(R.mipmap.audio_pause_big_icon);
                }
                break;
            case R.id.img_next_music_playing_activity:
                playMusicService.nextPlay();
                changeUI();
                break;
            case R.id.img_musiclist_music_playing_activity:

                break;
            case R.id.img_playstate_music_playing_activity:
                mState++;
                int flag = mState % 3;
                switch (flag) {
                    case PlayMusicService.FLAG_PLAY_INORDER:
                        imgPlayMusicState.setImageResource(R.mipmap.audio_order_play_icon);
                        playMusicService.setHowPlay(PlayMusicService.FLAG_PLAY_INORDER);
                        break;
                    case PlayMusicService.FLAG_PLAY_RANDOM:
                        imgPlayMusicState.setImageResource(R.mipmap.audio_random_play_icon);
                        playMusicService.setHowPlay(PlayMusicService.FLAG_PLAY_RANDOM);
                        break;
                    case PlayMusicService.FLAG_PLAY_THE_ONLY_ONE:
                        imgPlayMusicState.setImageResource(R.mipmap.audio_only_one_icon);
                        playMusicService.setHowPlay(PlayMusicService.FLAG_PLAY_THE_ONLY_ONE);
                        break;
                }
                break;
            case R.id.img_download_music_playing_activity:

                break;
            case R.id.img_focus_music_playing_activity:

                break;
            case R.id.img_more_music_playing_activity:

                break;
        }
    }

    private void changeUI() {
        long song_id = playMusicService.getCurrentSongId();
        MusicInfo musicInfo = MediaUtils.getChooseItemFromDB((int) song_id);
        textSongName.setText(musicInfo.songName);
        textAlbumTitle.setText(musicInfo.artistName + " - " + musicInfo.songName);
        textDuration.setText(formatTime(musicInfo.duration));
        if (PlayMusicService.isPlay) {
            imgPlayMusic.setImageResource(R.mipmap.audio_pause_big_icon);
        } else {
            imgPlayMusic.setImageResource(R.mipmap.audio_play_big_icon);
        }
        Bitmap bitmap = BitmapFactory.decodeFile(musicInfo.albumIMGUri);
        if (bitmap != null) {
            imgAlbumIcon.setImageBitmap(bitmap);
        }
    }

    private void changeUI(int position) {
        MusicInfo musicInfo = PlayMusicService.musicList.get(position);
        textSongName.setText(musicInfo.songName);
        textAlbumTitle.setText(musicInfo.artistName + " - " + musicInfo.songName);
        textDuration.setText(formatTime(musicInfo.duration));
        if (PlayMusicService.isPlay) {
            imgPlayMusic.setImageResource(R.mipmap.audio_pause_big_icon);
        } else {
            imgPlayMusic.setImageResource(R.mipmap.audio_play_big_icon);
        }
        Bitmap bitmap = BitmapFactory.decodeFile(musicInfo.albumIMGUri);
        if (bitmap != null) {
            imgAlbumIcon.setImageBitmap(bitmap);
        }
    }
}
