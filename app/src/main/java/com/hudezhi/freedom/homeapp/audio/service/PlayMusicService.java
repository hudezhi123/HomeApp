package com.hudezhi.freedom.homeapp.audio.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hudezhi.freedom.homeapp.MainActivity;
import com.hudezhi.freedom.homeapp.audio.bean.MusicInfo;
import com.hudezhi.freedom.homeapp.audio.utils.MediaUtils;
import com.hudezhi.freedom.homeapp.audio.utils.MusicDBHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by boy on 2017/6/5.
 * 实现功能：
 * 1、播放
 * 2、暂停
 * 3、上一首、下一首
 * 4、停止
 * 5、获取当前播放进度
 * 6、
 */

public class PlayMusicService extends Service {
    private MusicUpdateListener musicUpdateListener;
    private MusicUpdateProgressListener musicUpdateProgressListener;
    private MusicStateChangedListener musicStateChangedListener;
    private MusicPositionChangeListener musicPositionChangeListener;
    private MediaPlayer mPlayer;
    private int currentItem;  //当前正在播放的歌曲的位置
    public static final int FLAG_PLAY_THE_ONLY_ONE = 0;
    public static final int FLAG_PLAY_INORDER = 1;
    public static final int FLAG_PLAY_RANDOM = 2;
    private int howPlay = FLAG_PLAY_INORDER;
    private int currentProgress;
    public static List<MusicInfo> musicList;

    private ExecutorService es = Executors.newSingleThreadExecutor();
    public static boolean isPlay = false;
    Runnable updateState = new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (musicUpdateProgressListener != null) {
                    musicUpdateProgressListener.onUpdateProgress(getCurrentProgress());
                }
            }
        }
    };

    public PlayMusicService() {
    }


    /**
     * 获取音乐的播放方式（单曲循环、顺序播放、随机播放）
     *
     * @return
     */
    public int getHowPlay() {
        return howPlay;
    }

    public void setHowPlay(int howPlay) {
        this.howPlay = howPlay;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = new MediaPlayer();
        musicList = new ArrayList<>();
        musicList = MediaUtils.getDataFromDB();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (howPlay == FLAG_PLAY_INORDER) {
                    nextPlay();
                } else if (howPlay == FLAG_PLAY_THE_ONLY_ONE) {
                    playCurrentPosition(currentItem);
                } else if (howPlay == FLAG_PLAY_RANDOM) {
                    int count = musicList.size();
                    currentItem = (int) (Math.random() * count);
                    playCurrentPosition(currentItem);
                }
                musicPositionChangeListener.positionChange(currentItem);
            }
        });
        es.execute(updateState);
    }

    public class PlayBinder extends Binder {
        public PlayMusicService getPlayMusicService() {
            return PlayMusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new PlayBinder();
    }

    public void playCurrentPosition(int position) {
        MusicDBHelper dbHelper = new MusicDBHelper(getApplicationContext());
        if (position >= 0 && position < musicList.size()) {
            MusicInfo musicInfo = musicList.get(position);
            int songId = (int) musicInfo.id;
            musicInfo = MediaUtils.getChooseItemFromDB(songId);
            mPlayer.reset();
            if (mPlayer != null) {
                try {
                    mPlayer.setDataSource(this, Uri.parse(musicInfo.url));
                    mPlayer.prepare();
                    mPlayer.start();
                    isPlay = true;
                    currentItem = position;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    musicInfo.recentPlayTime = format.format(System.currentTimeMillis());
                    String sql_update = "update " + MusicDBHelper.TB_MY_MUSIC_INFO + " set "
                            + MusicDBHelper.IS_PLAYED + "='"
                            + String.valueOf(true) + "', "
                            + MusicDBHelper.RECENT_PLAY_TIME + "='"
                            + musicInfo.recentPlayTime + "' where "
                            + MusicDBHelper.SONG_ID
                            + "= '" + musicInfo.id +
                            "';";
                    dbHelper.execSQL(sql_update);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (musicUpdateListener != null) {
                    musicUpdateListener.onChange(songId);
                }
            }
        }
    }

    public void start() {
        if (mPlayer != null && !mPlayer.isPlaying()) {
            mPlayer.start();
            if (musicStateChangedListener != null) {
                musicStateChangedListener.playState(true);
            }
            isPlay = true;
        }
    }

    public void pausePlay() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            int position = mPlayer.getCurrentPosition();
            if (musicStateChangedListener != null) {
                musicStateChangedListener.playState(false);
            }
            isPlay = false;
        }
    }

    public MusicInfo getCurrenPositionItem(int position) {
        return musicList.get(position);
    }

    public int getCurrentSongId() {
        if (musicList == null || musicList.size() <= 0) {
            return -1;
        } else {
            return (int) musicList.get(currentItem).id;
        }

    }

    public String getCurrentAlbumUri() {
        return musicList.get(currentItem).albumIMGUri;
    }

    public boolean isPlaying() {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                return true;
            }
        }
        return false;
    }

    public void prePlay() {
        if (currentItem - 1 < 0) {
            currentItem = musicList.size() - 1;
        } else {
            currentItem--;
        }
        playCurrentPosition(currentItem);
    }

    public void nextPlay() {
        if (currentItem >= musicList.size() - 1) {
            currentItem = 0;
        } else {
            currentItem++;
        }
        playCurrentPosition(currentItem);
    }

    public int getDuration() {
        if (mPlayer != null) {
            return mPlayer.getDuration();
        } else {
            return -1;
        }

    }


    public void seekTo(int msec) {
        mPlayer.seekTo(msec);
        mPlayer.start();
    }


    public int getCurrentProgress() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            return mPlayer.getCurrentPosition();
        }
        return 0;
    }

    public void setCurrentProgress(int currentProgress) {
        seekTo(currentProgress);
    }

    //更新状态的接口
    public interface MusicUpdateListener {

        public void onChange(int songId);
    }

    public interface MusicUpdateProgressListener {
        public void onUpdateProgress(int progress);
    }

    public interface MusicStateChangedListener {
        public void playState(boolean isPlay);
    }

    public interface MusicPositionChangeListener {
        public void positionChange(int position);
    }

    public void setMusicPositionChangeListener(MusicPositionChangeListener musicPositionChangeListener) {
        this.musicPositionChangeListener = musicPositionChangeListener;
    }

    public void setMusicUpdateListener(MusicUpdateListener musicUpdateListener) {
        this.musicUpdateListener = musicUpdateListener;
    }

    public void setMusicUpdateProgressListener(MusicUpdateProgressListener listener) {
        this.musicUpdateProgressListener = listener;
    }

    public void setMusicStateChanged(MusicStateChangedListener musicStateChangedListener) {
        this.musicStateChangedListener = musicStateChangedListener;
    }

}
