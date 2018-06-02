package com.hudezhi.freedom.homeapp.bluetooth.bean;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by boy on 2017/9/28.
 */

public class BondDeviceInfo implements Parcelable {
    private String name;
    private String address;
    private BluetoothDevice device;

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

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    protected BondDeviceInfo(Parcel in) {
    }

    public static final Creator<BondDeviceInfo> CREATOR = new Creator<BondDeviceInfo>() {
        @Override
        public BondDeviceInfo createFromParcel(Parcel in) {
            return new BondDeviceInfo(in);
        }

        @Override
        public BondDeviceInfo[] newArray(int size) {
            return new BondDeviceInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public boolean equals(Object o) {
        boolean name = this.getName().equals(((BondDeviceInfo) o).getName());
        boolean address = this.getAddress().equals(((BondDeviceInfo) o).getAddress());
        if (name && address) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
