package com.hudezhi.freedom.homeapp.network.bean;

import java.io.Serializable;

/**
 * Created by boy on 2017/9/30.
 */

public class WifiItemInfo implements Serializable {
    private String bssId;  //mac地址
    private String ssId;   //名称
    private int netID;    //
    private int priority;
    private int status;
    private int signalLevel;    //强度
    private boolean isConnect;

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public WifiItemInfo() {

    }

    public String getBssId() {
        return bssId;
    }

    public void setBssId(String bssId) {
        this.bssId = bssId;
    }

    public String getSsId() {
        return ssId;
    }

    public void setSsId(String ssId) {
        this.ssId = ssId;  // .substring(1, ssId.length() - 1)
    }

    public int getNetID() {
        return netID;
    }

    public void setNetID(int netID) {
        this.netID = netID;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSignalLevel() {
        return signalLevel;
    }

    public void setSignalLevel(int signalLevel) {
        this.signalLevel = signalLevel;
    }
}
