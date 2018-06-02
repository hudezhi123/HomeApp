package com.hudezhi.freedom.homeapp.live_video.bean;

import java.io.Serializable;

/**
 * Created by boy on 2017/8/11.
 */

public class NetworkBean implements Serializable {

    private int type;
    private String name;
    private static final String CU = "cunet";
    private static final String CM = "cmnet";
    private static final String CT = "ctnet";

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        switch (name) {
            case CU:
                this.name = "中国联通";
                break;
            case CM:
                this.name = "中国移动";
                break;
            case CT:
                this.name = "中国电信";
                break;
            default:
                this.name = name;
                break;
        }
    }

}
