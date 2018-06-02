package com.hudezhi.freedom.homeapp.video.bean;

import java.io.Serializable;

/**
 * Created by hudezhi on 2017/3/12.
 */

public class LocalVideoBean implements Serializable {
    private String name;   //video name
    private long timeLength;   //how long is the video
    private long capcity;   //the capcity(体积) of the video
    private long timeSeenLength;   //how long have the video play(this data will build a database to save)
    private boolean isPlayed;   //judge whether the video has been played
    private String path;
    private String artist;
    private String title;
    private String bucketDisplayName;
    private String category;
    private String album;
    private String description;
    private String bookMark;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBucketDisplayName() {
        return bucketDisplayName;
    }

    public void setBucketDisplayName(String bucketDisplayName) {
        this.bucketDisplayName = bucketDisplayName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookMark() {
        return bookMark;
    }

    public void setBookMark(String bookMark) {
        this.bookMark = bookMark;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(long timeLength) {
        this.timeLength = timeLength;
    }

    public long getCapcity() {
        return capcity;
    }

    public void setCapcity(long capcity) {
        this.capcity = capcity;
    }

    public long getTimeSeenLength() {
        return timeSeenLength;
    }

    public void setTimeSeenLength(long timeSeenLength) {
        this.timeSeenLength = timeSeenLength;
    }

    @Override
    public String toString() {
        return "LocalVideoBean{" +
                "name='" + name + '\'' +
                ", timeLength=" + timeLength +
                ", capcity=" + capcity +
                ", timeSeenLength=" + timeSeenLength +
                ", isPlayed=" + isPlayed +
                ", path='" + path + '\'' +
                '}';
    }
}
