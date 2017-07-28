package com.example.administrator.kib_3plus.view.fragment.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.CameraUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.mode.RaceMainMode;
import com.example.administrator.kib_3plus.mode.RewardsListMode;
import com.example.administrator.kib_3plus.ui.RoundImageView;
import com.example.administrator.kib_3plus.ui.SlidingButtonView;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.appscomm.db.mode.ChildInfoDB;
import cn.appscomm.db.mode.RewardsL28TDB;

import static android.media.CamcorderProfile.get;
import static com.example.administrator.kib_3plus.R.id.c_r_item_date_tv;
import static com.example.administrator.kib_3plus.R.id.c_r_item_gold_tv;
import static com.example.administrator.kib_3plus.R.id.layout_content;
import static com.example.administrator.kib_3plus.R.id.race_icon_riv;

/**
 * Created by cui on 2017/7/14.
 */

public class RaceMainRecyclerAdapter extends RecyclerView.Adapter<RaceMainRecyclerAdapter.MyViewHolder>  {

    private static String TAG="RewardsRecyclerAdapter";
    private Context mContext;
    private List<RaceMainMode> mDatas = new ArrayList<RaceMainMode>();
    public RaceMainRecyclerAdapter(Context context
            , List<RaceMainMode> data) {
        mContext = context;
        mDatas=data;
    }
    private  MyItemClickListener mItemClickListener;

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        RaceMainMode raceMainMode=  mDatas.get(position);
        if(raceMainMode.isClick()){
            holder.race_item_main_ll.setBackgroundColor(mContext.getResources().getColor(R.color.colorText));

        }else{
            holder.race_item_main_ll.setBackground(null);

        }
        holder.race_icon_riv.setBackgroundPaint(raceMainMode.getFavorite());

        if(raceMainMode.isIcon()){
            holder.race_icon_riv.setImageResource(raceMainMode.getIcon());
        }else{
            LogUtils.i(raceMainMode.getIconUrl());
            String poth=CameraUtils.INSTANCE.getIcon(raceMainMode.getIconUrl());
            Bitmap bitmap=  CameraUtils.INSTANCE.getSmallBitmap(poth);
            if(bitmap!=null){
                LogUtils.i(raceMainMode.getIconUrl());
                holder.race_icon_riv.setImageBitmap(bitmap);
            }
            bitmap=null;

        }

    }

    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.race_main_item, arg0,false);
        MyViewHolder holder = new MyViewHolder(view,mItemClickListener);
        return holder;
    }



    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        RoundImageView race_icon_riv;
        private MyItemClickListener mListener;
        private LinearLayout race_item_main_ll;
        public MyViewHolder(View itemView,MyItemClickListener listener) {
            super(itemView);
            this.mListener=listener;
            race_icon_riv=(RoundImageView)itemView.findViewById(R.id.race_icon_riv);
            race_item_main_ll=(LinearLayout)itemView.findViewById(R.id.race_item_main_ll);
            race_icon_riv.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getPosition());
            }
        }
    }
}
