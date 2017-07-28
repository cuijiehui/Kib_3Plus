package com.example.administrator.kib_3plus.view.fragment.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kib_3plus.Utils.CameraUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.ui.RoundImageView;
import com.example.administrator.kib_3plus.ui.SemicircleBar;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;

import java.util.List;

import cn.appscomm.db.mode.SpoetL28TDB;

import static android.media.CamcorderProfile.get;
import static com.example.administrator.kib_3plus.R.id.main_member_riv;

/**
 * Created by cui on 2017/7/3.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>  {

    private Context mContext;
    private List<SpoetL28TDB> mDatas;
    private boolean isEdit=false;
    MyItemClickListener mItemClickListener;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.main_member_item, parent,
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
    public HomeAdapter(Context context,List<SpoetL28TDB> mDatas) {
        mContext=context;
        this.mDatas=mDatas;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        SpoetL28TDB spoetL28TDB= mDatas.get(position);
        holder.main_member_name_tv.setText(spoetL28TDB.getName());
       int activityPercent=(int) ((float)((spoetL28TDB.getActivity()*180)/500));
        LogUtils.i("activityPercent="+activityPercent+"--mDatas.get(position).getActivity()="+spoetL28TDB.getActivity());
        holder.main_member_activity_sb.setSweepAngle(activityPercent);
        int choresPercent= (spoetL28TDB.getChores()*180/1000/500);

        LogUtils.i("choresPercent="+choresPercent+"--mDatas.get(position).getChores()="+spoetL28TDB.getChores());
        holder.main_member_chores_sb.setSweepAngle(choresPercent);

        if(isEdit){
            holder.main_member_delete_iv.setVisibility(View.VISIBLE);
        }else{
            holder.main_member_delete_iv.setVisibility(View.GONE);
        }

        if(spoetL28TDB.isIcon()){
            holder.main_member_riv.setImageResource(spoetL28TDB.getIcon());
        }else{
            LogUtils.i(spoetL28TDB.getIconUrl());
            String poth=CameraUtils.INSTANCE.getIcon(spoetL28TDB.getIconUrl());
            Bitmap bitmap=  CameraUtils.INSTANCE.getSmallBitmap(poth);
            if(bitmap!=null){
                LogUtils.i(spoetL28TDB.getIconUrl());
                holder.main_member_riv.setImageBitmap(bitmap);
            }
            bitmap=null;

        }
        if(spoetL28TDB.getFavorite()!=-1){
            holder.main_member_riv.setBackgroundPaint(spoetL28TDB.getFavorite());
        }

    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener
    {

        TextView main_member_name_tv;
        SemicircleBar main_member_activity_sb,main_member_chores_sb;
        ImageView main_member_delete_iv;
        RoundImageView main_member_riv;
        private MyItemClickListener mListener;
        public MyViewHolder(View view,MyItemClickListener listener)
        {
            super(view);
            this.mListener = listener;
            main_member_name_tv = (TextView) view.findViewById(R.id.main_member_name_tv);
            main_member_activity_sb = (SemicircleBar) view.findViewById(R.id.main_member_activity_sb);
            main_member_chores_sb = (SemicircleBar) view.findViewById(R.id.main_member_chores_sb);
            main_member_delete_iv = (ImageView) view.findViewById(R.id.main_member_delete_iv);
            main_member_riv = (RoundImageView) view.findViewById(R.id.main_member_riv);
            main_member_delete_iv.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getPosition());
            }
        }
    }
    public void setEdit(boolean isEdit){
        this.isEdit=isEdit;
        notifyDataSetChanged();
    }
}
