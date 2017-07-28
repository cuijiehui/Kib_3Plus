package com.example.administrator.kib_3plus.view.fragment.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.ui.RoundImageView;
import com.example.administrator.kib_3plus.ui.SemicircleBar;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;

import java.util.List;

import cn.appscomm.db.mode.SpoetL28TDB;

import static android.media.CamcorderProfile.get;
import static com.example.administrator.kib_3plus.R.id.main_member_activity_sb;
import static com.example.administrator.kib_3plus.R.id.main_member_chores_sb;
import static com.example.administrator.kib_3plus.R.id.main_member_delete_iv;
import static com.example.administrator.kib_3plus.R.id.main_member_name_tv;
import static com.example.administrator.kib_3plus.R.id.main_member_riv;

/**
 * Created by cui on 2017/7/3.
 */

public class AddMemberIconAdapter extends RecyclerView.Adapter<AddMemberIconAdapter.MyViewHolder>  {

    private Context mContext;
    private List<Integer> mDatas;
    private boolean isEdit=false;
    MyItemClickListener mItemClickListener;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.add_member_icon_item, parent,
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
    public AddMemberIconAdapter(Context context, List<Integer> mDatas) {
        mContext=context;
        this.mDatas=mDatas;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        int iconI=mDatas.get(position);
        holder.add_icon_item_riv.setImageResource(iconI);

    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener
    {

        private RoundImageView  add_icon_item_riv;
        private MyItemClickListener mListener;
        public MyViewHolder(View view,MyItemClickListener listener)
        {
            super(view);
            this.mListener = listener;
            add_icon_item_riv=(RoundImageView) view.findViewById(R.id.add_icon_item_riv);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getPosition());
            }
        }
    }

}
