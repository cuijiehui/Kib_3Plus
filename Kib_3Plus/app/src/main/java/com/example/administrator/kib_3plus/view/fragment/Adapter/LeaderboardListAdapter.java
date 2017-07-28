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
import com.example.administrator.kib_3plus.mode.LeaderboardMode;
import com.example.administrator.kib_3plus.ui.RoundImageView;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;

import java.util.List;

import cn.appscomm.db.mode.SpoetL28TDB;

import static android.content.ContentValues.TAG;
import static android.media.CamcorderProfile.get;
import static com.example.administrator.kib_3plus.R.id.data_day_icon_riv;
import static com.example.administrator.kib_3plus.R.id.leaderboard_icon_riv;

/**
 * Created by cui on 2017/7/5.
 */

public class LeaderboardListAdapter extends RecyclerView.Adapter<LeaderboardListAdapter.LViewHolder> {

    private Context mContext;
    private List<LeaderboardMode> mListData;
    private  MyItemClickListener mItemClickListener;

    @Override
    public LViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LViewHolder holder=new LViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.leaderboard_list_item, parent,
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

    public LeaderboardListAdapter(Context mContext, List mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
    }

    @Override
    public void onBindViewHolder(LViewHolder holder, int position) {
        LeaderboardMode leaderboardMode= mListData.get(position);
        holder.leaderboard_index_tv.setText(position+1+"");
        holder.leaderboard_name_tv.setText(leaderboardMode.getName());
        holder.leaderboard_stpe_tv.setText(leaderboardMode.getStep()+"");
        long updateTime = leaderboardMode.getTime();
        long endTime = System.currentTimeMillis() / 1000;
        int lastDate = (int) (endTime - updateTime) / 60;
        String time = "";//"刚刚"

        if (lastDate < 15) {
            time = mContext.getResources().getString(R.string.leaderboard_now_tv); //"刚刚";
        } else if (lastDate < 60) {
            time = lastDate + " " + mContext.getResources().getString(R.string.leaderboard_minute_ago_tv);//"分钟";
        } else if (lastDate >= 60) {
            int H = lastDate / 60;
            int M = lastDate % 60;
            time = H + " " + mContext.getResources().getString(R.string.leaderboard_hours_ago_tv);
        }
        if (leaderboardMode.getStep()<=0) {
            time = "";
        }
        holder.leaderboard_update_time_tv.setText(time);
        holder. leaderboard_icon_riv.setBackgroundPaint(leaderboardMode.getFavorite());

        if(leaderboardMode.isIcon()){
            holder.leaderboard_icon_riv.setBackgroundResource(leaderboardMode.getIcon());
        }else{
            LogUtils.i(leaderboardMode.getUrl());
            String poth=CameraUtils.INSTANCE.getIcon(leaderboardMode.getUrl());
            Bitmap bitmap=  CameraUtils.INSTANCE.getSmallBitmap(poth);
            if(bitmap!=null){
                LogUtils.i(leaderboardMode.getUrl());
                holder.leaderboard_icon_riv.setImageBitmap(bitmap);
            }
            bitmap=null;

        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class LViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView leaderboard_index_tv,leaderboard_name_tv,leaderboard_stpe_tv,leaderboard_update_time_tv;
        RoundImageView leaderboard_icon_riv;
        private MyItemClickListener mListener;

        public LViewHolder(View itemView,MyItemClickListener listener) {
            super(itemView);
            this.mListener=listener;
            leaderboard_index_tv= (TextView)itemView.findViewById(R.id.leaderboard_index_tv);
            leaderboard_name_tv=(TextView)itemView.findViewById(R.id.leaderboard_name_tv);
            leaderboard_stpe_tv=(TextView)itemView.findViewById(R.id.leaderboard_stpe_tv);
            leaderboard_update_time_tv=(TextView) itemView.findViewById(R.id.leaderboard_update_time_tv);
            leaderboard_icon_riv=(RoundImageView) itemView.findViewById(R.id.leaderboard_icon_riv);
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
