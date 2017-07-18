package com.example.administrator.kib_3plus.view.fragment.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;

import java.util.List;

import cn.appscomm.db.mode.ChoresL28TDB;
import cn.appscomm.db.mode.RewardsL28TDB;

import static com.example.administrator.kib_3plus.R.id.chore_list_gold_tv;
import static com.example.administrator.kib_3plus.R.id.chore_list_icon_iv;
import static com.example.administrator.kib_3plus.R.id.chore_list_name_tv;

/**
 * Created by cui on 2017/7/5.
 */

public class RewardsListAdapter extends RecyclerView.Adapter<RewardsListAdapter.HViewHolder> {

    private Context mContext;
    private List<RewardsL28TDB> mListData;
    private  MyItemClickListener mItemClickListener;

    @Override
    public HViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HViewHolder holder=new HViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.rewards_list_item, parent,
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

    public RewardsListAdapter(Context mContext, List mListData) {
        this.mContext = mContext;
        this.mListData = mListData;
    }

    @Override
    public void onBindViewHolder(HViewHolder holder, int position) {
        RewardsL28TDB rewardsL28TDB= mListData.get(position);
        holder.rewards_list_name_tv.setText(rewardsL28TDB.getName());
        holder.rewards_list_gold_tv.setText(rewardsL28TDB.getGold()+"");
        holder.rewards_list_icon_iv.setImageResource(rewardsL28TDB.getIcon());
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class HViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView rewards_list_name_tv,rewards_list_gold_tv;
        ImageView rewards_list_icon_iv;
        Button rewards_list_redeem_bt;
        private MyItemClickListener mListener;

        public HViewHolder(View itemView,MyItemClickListener listener) {
            super(itemView);
            this.mListener=listener;
            rewards_list_name_tv= (TextView)itemView.findViewById(R.id.rewards_list_name_tv);
            rewards_list_gold_tv= (TextView)itemView.findViewById(R.id.rewards_list_gold_tv);
            rewards_list_icon_iv= (ImageView)itemView.findViewById(R.id.rewards_list_icon_iv);
            rewards_list_redeem_bt= (Button)itemView.findViewById(R.id.rewards_list_redeem_bt);
            itemView.setOnClickListener(this);
            rewards_list_redeem_bt.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getPosition());
            }
        }
    }
}
