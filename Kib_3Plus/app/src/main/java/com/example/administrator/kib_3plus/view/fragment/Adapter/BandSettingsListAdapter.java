package com.example.administrator.kib_3plus.view.fragment.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.CameraUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.mode.BandSettingsMode;
import com.example.administrator.kib_3plus.mode.LeaderboardMode;
import com.example.administrator.kib_3plus.ui.RoundImageView;
import com.example.administrator.kib_3plus.view.fragment.BandSettingsFragment;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;

import java.util.List;

import static android.R.attr.bitmap;

/**
 * Created by cui on 2017/7/5.
 */

public class BandSettingsListAdapter extends RecyclerView.Adapter<BandSettingsListAdapter.HViewHolder> {

    private Context mContext;
    private List<BandSettingsMode> mListData;
    private  MyItemClickListener mItemClickListener;

    @Override
    public HViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HViewHolder holder=new HViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.band_settings_item, parent,
                false),mItemClickListener);
        return holder;
    }
    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public BandSettingsListAdapter(Context mContext, List mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
    }

    @Override
    public void onBindViewHolder(HViewHolder holder, int position) {
        BandSettingsMode bandSettingsMode= mListData.get(position);
        holder.band_settings_name_tv.setText(bandSettingsMode.getName());
        holder.band_settings_icon_rv.setBackgroundPaint(bandSettingsMode.getFavorite());
        if(bandSettingsMode.isIcon()){
            holder.band_settings_icon_rv.setImageResource(bandSettingsMode.getIcon());
        }else{
            LogUtils.i(bandSettingsMode.getUrl());
            String poth=CameraUtils.INSTANCE.getIcon(bandSettingsMode.getUrl());
            Bitmap bitmap=  CameraUtils.INSTANCE.getSmallBitmap(poth);
            if(bitmap!=null){
                LogUtils.i(bandSettingsMode.getUrl());
                holder.band_settings_icon_rv.setImageBitmap(bitmap);
            }
            bitmap=null;
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class HViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView band_settings_name_tv;
        RoundImageView band_settings_icon_rv;
        private MyItemClickListener mListener;

        public HViewHolder(View itemView,MyItemClickListener listener) {
            super(itemView);
            this.mListener=listener;
            band_settings_icon_rv= (RoundImageView)itemView.findViewById(R.id.band_settings_icon_rv);
            band_settings_name_tv=(TextView)itemView.findViewById(R.id.band_settings_name_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getPosition());
            }
        }
    }
}
