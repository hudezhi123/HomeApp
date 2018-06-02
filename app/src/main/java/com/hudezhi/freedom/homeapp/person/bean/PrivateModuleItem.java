package com.hudezhi.freedom.homeapp.person.bean;

import java.io.Serializable;

/**
 * Created by boy on 2017/8/7.
 */

public class PrivateModuleItem implements Serializable {
    private int id;
    private String tag;
    private String moduleName;
    private int imgId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
