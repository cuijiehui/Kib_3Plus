package com.example.administrator.kib_3plus;

import java.io.Serializable;

/**
 * Created by cui on 2017/6/10.
 */

public class ScanDeviceModel implements Serializable{
    public String deviceName;
    public String deviceID;

    public ScanDeviceModel(String deviceName, String deviceID) {
        this.deviceName = deviceName;
        this.deviceID = deviceID;
    }

    @Override
    public String toString() {
        return "ScanDeviceModel{" +
                "deviceName='" + deviceName + '\'' +
                ", deviceID='" + deviceID + '\'' +
                '}';
    }
}
