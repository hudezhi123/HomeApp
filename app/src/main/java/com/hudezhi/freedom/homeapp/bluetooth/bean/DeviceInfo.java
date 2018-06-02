package com.hudezhi.freedom.homeapp.bluetooth.bean;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hudezhi.freedom.homeapp.map_baidu.activity.SearchMapActivity;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by boy on 2017/8/18.
 */

public class DeviceInfo implements Parcelable {
    private String name;
    private String address;
    private BluetoothDevice device;

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public boolean equals(Object o) {
        if (TextUtils.isEmpty(this.getName())) {
            if (TextUtils.isEmpty(((DeviceInfo) o).getName())) {
                return true;
            }else{
                return false;
            }
        }
        if(TextUtils.isEmpty(((DeviceInfo) o).getName())){
            return false;
        }
        boolean name = this.getName().equals(((DeviceInfo) o).getName());
        boolean address = this.getAddress().equals(((DeviceInfo) o).getAddress());
        if (name && address) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeParcelable(device, flags);
    }

    public static final Parcelable.Creator<DeviceInfo> CREATOR = new Parcelable.Creator<DeviceInfo>() {
        @Override
        public DeviceInfo createFromParcel(Parcel source) {
            DeviceInfo info = new DeviceInfo();
            info.setName(source.readString());
            info.setAddress(source.readString());
            info.setDevice((BluetoothDevice) source.readParcelable(BluetoothDevice.class.getClassLoader()));
            return info;
        }

        @Override
        public DeviceInfo[] newArray(int size) {
            return new DeviceInfo[size];
        }
    };
}
