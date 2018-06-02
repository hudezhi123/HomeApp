package com.hudezhi.freedom.homeapp;

import java.io.Serializable;

/**
 * Created by boy on 2017/7/28.
 */

public class ModuleItem implements Serializable {
    private String tag;
    private int imgId;
    private String moduleName;

    public ModuleItem() {
    }

    public ModuleItem(String tag, String moduleName, int imgId) {
        this.tag = tag;
        this.imgId = imgId;
        this.moduleName = moduleName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }


}
