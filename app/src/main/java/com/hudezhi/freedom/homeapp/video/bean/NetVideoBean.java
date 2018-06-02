package com.hudezhi.freedom.homeapp.video.bean;

import java.io.Serializable;

/**
 * Created by boy on 2017/8/24.
 */

public class NetVideoBean implements Serializable {
    private int id;
    private String fileUrl;
    private String filePath;
    private long fileSize;
    private long filePausePoint;
    private boolean isFinish;
    private boolean isLoading;
    private String thumbnailPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getFilePausePoint() {
        return filePausePoint;
    }

    public void setFilePausePoint(long filePausePoint) {
        this.filePausePoint = filePausePoint;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
}