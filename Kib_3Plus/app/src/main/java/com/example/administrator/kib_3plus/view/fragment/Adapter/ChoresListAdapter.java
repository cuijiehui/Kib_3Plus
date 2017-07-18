package com.example.administrator.kib_3plus.view.fragment.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.mode.BandSettingsMode;
import com.example.administrator.kib_3plus.ui.RoundImageView;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;

import java.util.List;

import cn.appscomm.db.mode.ChoresL28TDB;

import static android.media.CamcorderProfile.get;
import static com.example.administrator.kib_3plus.R.id.band_settings_icon_rv;
import static com.example.administrator.kib_3plus.R.id.band_settings_name_tv;

/**
 * Created by cui on 2017/7/5.
 */

public class ChoresListAdapter extends RecyclerView.Adapter<ChoresListAdapter.HViewHolder> {

    private Context mContext;
    private List<ChoresL28TDB> mListData;
    private  MyItemClickListener mItemClickListener;

    @Override
    public HViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HViewHolder holder=new HViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.chores_list_item, parent,
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

    public ChoresListAdapter(Context mContext, List mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
    }

    @Override
    public void onBindViewHolder(HViewHolder holder, int position) {
        ChoresL28TDB choresL28TDB= mListData.get(position);
        holder.chore_list_gold_tv.setText("X "+choresL28TDB.getGold()+"");
        holder.chore_list_name_tv.setText(choresL28TDB.getName()+"");
        holder.chore_list_icon_iv.setImageResource(choresL28TDB.getIcon());
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class HViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView chore_list_name_tv,chore_list_gold_tv;
        ImageView chore_list_icon_iv;
        private MyItemClickListener mListener;

        public HViewHolder(View itemView,MyItemClickListener listener) {
            super(itemView);
            this.mListener=listener;
            chore_list_icon_iv= (ImageView)itemView.findViewById(R.id.chore_list_icon_iv);
            chore_list_gold_tv=(TextView)itemView.findViewById(R.id.chore_list_gold_tv);
            chore_list_name_tv=(TextView)itemView.findViewById(R.id.chore_list_name_tv);
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
