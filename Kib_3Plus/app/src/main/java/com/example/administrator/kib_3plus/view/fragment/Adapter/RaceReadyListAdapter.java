package com.example.administrator.kib_3plus.view.fragment.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.CameraUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.ui.MySeekBar;
import com.example.administrator.kib_3plus.ui.RoundImageView;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;

import java.util.List;

import cn.appscomm.db.mode.ChoresL28TDB;
import cn.appscomm.db.mode.RaceDB;

import static android.graphics.BitmapFactory.decodeResource;
import static com.example.administrator.kib_3plus.R.id.chore_list_gold_tv;
import static com.example.administrator.kib_3plus.R.id.race_motion_sb;

/**
 * Created by cui on 2017/7/5.
 */

public class RaceReadyListAdapter extends RecyclerView.Adapter<RaceReadyListAdapter.HViewHolder> {

    private Context mContext;
    private List<RaceDB> mListData;
    private  MyItemClickListener mItemClickListener;

    @Override
    public HViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HViewHolder holder=new HViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.race_motion_item, parent,
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

    public RaceReadyListAdapter(Context mContext, List mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
    }

    @Override
    public void onBindViewHolder(HViewHolder holder, int position) {
        RaceDB raceDB= mListData.get(position);
        holder.race_motion_icon_riv.setBackgroundPaint(raceDB.getFavorite());
        if(raceDB.isIcon()){
            holder.race_motion_icon_riv.setImageResource(raceDB.getIcon());
        }else{
            LogUtils.i(raceDB.getUrl());
            String poth=CameraUtils.INSTANCE.getIcon(raceDB.getUrl());
            Bitmap bitmap=  CameraUtils.INSTANCE.getSmallBitmap(poth);
            if(bitmap!=null){
                LogUtils.i(raceDB.getUrl());
                holder.race_motion_icon_riv.setImageBitmap(bitmap);
            }
            bitmap=null;

        }
        Bitmap bitmp=BitmapFactory.decodeResource(mContext.getResources(),raceDB.getBgIcon() );
        holder.race_motion_sb.setData(raceDB.getStep(),bitmp,raceDB.getGoal());
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class HViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RoundImageView race_motion_icon_riv;
        private MySeekBar race_motion_sb;
        private MyItemClickListener mListener;

        public HViewHolder(View itemView,MyItemClickListener listener) {
            super(itemView);
            this.mListener=listener;
            race_motion_icon_riv=(RoundImageView) itemView.findViewById(R.id.race_motion_icon_riv);
            race_motion_sb=(MySeekBar) itemView.findViewById(R.id.race_motion_sb);
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
