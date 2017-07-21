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
import com.example.administrator.kib_3plus.mode.RaceContinentListMode;
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

public class ContinentAdapter extends RecyclerView.Adapter<ContinentAdapter.MyViewHolder>  {

    private Context mContext;
    private List<RaceContinentListMode> mDatas;
    MyItemClickListener mItemClickListener;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.continent_item, parent,
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
    public ContinentAdapter(Context context, List<RaceContinentListMode> mDatas) {
        mContext=context;
        this.mDatas=mDatas;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        RaceContinentListMode raceContinentListMode= mDatas.get(position);
        holder.continent_icon_iv.setImageResource(raceContinentListMode.getIcon());
        holder.continent_name_tv.setText(raceContinentListMode.getName());
        holder.continent_steps_tv.setText(raceContinentListMode.getSteps()+" steps");
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener
    {

        TextView continent_name_tv,continent_steps_tv;
        ImageView continent_icon_iv;
        private MyItemClickListener mListener;
        public MyViewHolder(View view,MyItemClickListener listener)
        {
            super(view);
            this.mListener = listener;
            continent_name_tv=(TextView) view.findViewById(R.id.continent_name_tv);
            continent_steps_tv=(TextView)view.findViewById(R.id.continent_steps_tv);
            continent_icon_iv=(ImageView)view.findViewById(R.id.continent_icon_iv);
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
