package com.example.administrator.kib_3plus.view.fragment.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.mode.RaceMainMode;
import com.example.administrator.kib_3plus.ui.RoundImageView;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cui on 2017/7/14.
 */

public class RaceMainSeeRecyclerAdapter extends RecyclerView.Adapter<RaceMainSeeRecyclerAdapter.MyViewHolder>  {

    private static String TAG="RewardsRecyclerAdapter";
    private Context mContext;
    private List<RaceMainMode> mDatas = new ArrayList<RaceMainMode>();
    public RaceMainSeeRecyclerAdapter(Context context
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
        }
        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getPosition());
            }
        }
    }
}
