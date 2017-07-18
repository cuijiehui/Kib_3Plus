package com.example.administrator.kib_3plus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.kib_3plus.Utils.LogUtils;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

/**
 * Created by cui on 2017/6/10.
 */

public class ScanBleDeviceAdapter extends BaseAdapter {

private ArrayList<ScanDeviceModel> mDeviceList;
private LayoutInflater mLayoutInflater;
    private Holder mHolder;

    public ScanBleDeviceAdapter(Context context,ArrayList<ScanDeviceModel> mDeviceList) {
        this.mLayoutInflater=LayoutInflater.from(context);
        this.mDeviceList = mDeviceList;
    }

    @Override
    public int getCount() {
        if(mDeviceList!=null)
            return mDeviceList.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mDeviceList!=null&&mDeviceList.size()>0)
            return mDeviceList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ScanDeviceModel scanDeviceModel= mDeviceList.get(position);
        if(convertView==null){
            convertView= mLayoutInflater.inflate(R.layout.test_list_view_item_layout,null);
            mHolder=new Holder();
            mHolder.deviceNmae=(TextView) convertView.findViewById(R.id.tv_device_name);
            convertView.setTag(mHolder);
        }else{
            mHolder=(Holder) convertView.getTag();
        }
        int index=scanDeviceModel.deviceName.indexOf("#");
        String name=scanDeviceModel.deviceName.substring(index,scanDeviceModel.deviceName.length());
        name="Lite"+name;
        LogUtils.i("position="+scanDeviceModel.deviceName);
        mHolder.deviceNmae.setText(scanDeviceModel.deviceName);
        return convertView;
    }
    class Holder{
        public TextView deviceNmae;
    }
}
