package com.hudezhi.freedom.homeapp.bluetooth.bean;

import com.hyphenate.chat.EMMessage;

/**
 * Created by boy on 2017/8/21.
 */

public class HAPMessage {
    public static final int VIEW_TYPE_SEND = 1;
    public static final int VIEW_TYPE_RECEIVE = 2;

    public static final int MSG_TYPE_IMG = 1;
    public static final int MSG_TYPE_TXT = 2;
    public static final int MSG_TYPE_AUDIO = 3;
    public static final int MSG_TYPE_VIDEO = 4;
    private int viewType;
    private String name;
    private String content;
    private int msgType;
    private String createTime;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
