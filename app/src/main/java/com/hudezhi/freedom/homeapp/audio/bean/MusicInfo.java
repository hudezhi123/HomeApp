package com.hudezhi.freedom.homeapp.audio.bean;

import java.io.Serializable;

/**
 * Created by boy on 2017/6/2.
 */

public class MusicInfo implements Serializable {

    public long id;
    public String songName;
    public String artistName;
    public String album;
    public long albumId;
    public String url;
    public long size;
    public long duration;
    public int isMusic;
    public boolean isFocus;
    public String albumImgPath;
    public String albumIMGUri;
    public boolean isRecent;
    public boolean isPlayed;
    public String recentPlayTime;

}
