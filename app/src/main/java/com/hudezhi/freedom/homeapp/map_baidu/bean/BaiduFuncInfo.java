package com.hudezhi.freedom.homeapp.map_baidu.bean;

import java.io.Serializable;

/**
 * Created by boy on 2017/7/31.
 */

public class BaiduFuncInfo implements Serializable {

    private int imgId;
    private String funcName;
    private String tag;


    public BaiduFuncInfo() {
    }

    public BaiduFuncInfo(int imgid, String funcName, String tag) {
        this.imgId = imgid;
        this.funcName = funcName;
        this.tag = tag;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
