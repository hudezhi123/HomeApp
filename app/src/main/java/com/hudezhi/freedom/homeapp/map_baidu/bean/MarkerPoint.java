package com.hudezhi.freedom.homeapp.map_baidu.bean;

import java.io.Serializable;

/**
 * Created by boy on 2017/8/1.
 */

public class MarkerPoint implements Serializable {
    private float longitude;
    private float latitude;

    public MarkerPoint() {
    }

    public MarkerPoint(float latitude, float longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
