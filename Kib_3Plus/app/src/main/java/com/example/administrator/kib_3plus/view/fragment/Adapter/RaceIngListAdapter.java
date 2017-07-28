package com.example.administrator.kib_3plus.view.fragment.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.CameraUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.ui.MySeekBar;
import com.example.administrator.kib_3plus.ui.RoundImageView;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;

import java.util.List;

import cn.appscomm.db.mode.RaceDB;

import static android.R.attr.onClick;
import static com.example.administrator.kib_3plus.R.id.race_motion_ing_main_rl;

/**
 * Created by cui on 2017/7/5.
 */

public class RaceIngListAdapter extends RecyclerView.Adapter<RaceIngListAdapter.HViewHolder> {

    private Context mContext;
    private List<RaceDB> mListData;
    private  MyItemClickListener mItemClickListener;
    private  View.OnClickListener mClickListener;
    private int onClickPosition=-1;

    @Override
    public HViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HViewHolder holder=new HViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.race_motion_ing_item, parent,
                false),mItemClickListener);
        return holder;
    }
    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(View.OnClickListener clickListener,MyItemClickListener listener){
        this.mClickListener=clickListener;
        this.mItemClickListener = listener;
    }

    public RaceIngListAdapter(Context mContext, List mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
    }
    public void setOnClickPosition(int position){
        onClickPosition=position;
    }

    @Override
    public void onBindViewHolder(HViewHolder holder, int position) {
        RaceDB raceDB= mListData.get(position);
        holder.race_motion_ing_icon_riv.setBackgroundPaint(raceDB.getFavorite());
        if(raceDB.isIcon()){
            holder.race_motion_ing_icon_riv.setImageResource(raceDB.getIcon());
        }else{
            LogUtils.i(raceDB.getUrl());
            String poth=CameraUtils.INSTANCE.getIcon(raceDB.getUrl());
            Bitmap bitmap=  CameraUtils.INSTANCE.getSmallBitmap(poth);
            if(bitmap!=null){
                LogUtils.i(raceDB.getUrl());
                holder.race_motion_ing_icon_riv.setImageBitmap(bitmap);
            }
            bitmap=null;

        }
        Bitmap bitmp=BitmapFactory.decodeResource(mContext.getResources(),raceDB.getBgIcon() );
        holder.race_motion_ing_sb.setData(raceDB.getStep(),bitmp,raceDB.getGoal());
        holder.race_motion_ing_step_tv.setText(raceDB.getStep()+" steps");

        if(onClickPosition>=0&&onClickPosition==position){
            holder.race_ing_btn_ll.setVisibility(View.VISIBLE);
            holder.race_motion_ing_main_rl.setBackgroundResource(R.color.colorYellow);
        }else{
            holder.race_ing_btn_ll.setVisibility(View.GONE);
            holder.race_motion_ing_main_rl.setBackgroundResource(R.color.colorWhite);

        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class HViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RoundImageView race_motion_ing_icon_riv;
        private MySeekBar race_motion_ing_sb;
        private TextView race_motion_ing_step_tv;
        private Button race_ing_leave_bt,race_ing_cancel_bt;
        private LinearLayout race_ing_btn_ll;
        private RelativeLayout race_motion_ing_main_rl;
        private MyItemClickListener mListener;

        public HViewHolder(View itemView,MyItemClickListener listener) {
            super(itemView);
            this.mListener=listener;
            race_motion_ing_icon_riv=(RoundImageView) itemView.findViewById(R.id.race_motion_ing_icon_riv);
            race_motion_ing_sb=(MySeekBar) itemView.findViewById(R.id.race_motion_ing_sb);
            race_motion_ing_step_tv=(TextView) itemView.findViewById(R.id.race_motion_ing_step_tv);
            race_ing_leave_bt=(Button) itemView.findViewById(R.id.race_ing_leave_bt);
            race_ing_cancel_bt=(Button) itemView.findViewById(R.id.race_ing_cancel_bt);
            race_ing_btn_ll=(LinearLayout) itemView.findViewById(R.id.race_ing_btn_ll);
            race_motion_ing_main_rl=(RelativeLayout) itemView.findViewById(R.id.race_motion_ing_main_rl);
            race_ing_leave_bt.setOnClickListener(mClickListener);
            race_ing_cancel_bt.setOnClickListener(mClickListener);
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
