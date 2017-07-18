package com.example.administrator.kib_3plus.view.fragment.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.mode.PictureDialogMode;
import com.example.administrator.kib_3plus.ui.RoundImageView;
import com.example.administrator.kib_3plus.view.fragment.interfaces.MyItemClickListener;

import java.util.List;

/**
 * Created by cui on 2017/7/4.
 */

public class PictureDialogAdapter extends RecyclerView.Adapter<PictureDialogAdapter.MyViewHolderPD> {


    private Context mContext;
    private List<PictureDialogMode> mDatas;
    private boolean isEdit=false;
    MyItemClickListener mItemClickListener;

    @Override
    public PictureDialogAdapter.MyViewHolderPD onCreateViewHolder(ViewGroup parent, int viewType)
    {
        PictureDialogAdapter.MyViewHolderPD holder = new PictureDialogAdapter.MyViewHolderPD(LayoutInflater.from(
                mContext).inflate(R.layout.picture_dialog_item, parent,
                false),mItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolderPD holder, int position) {
        holder.picture_dialog_item_rv.setImageBitmap(mDatas.get(position).getBitmap());

    }

    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }
    public PictureDialogAdapter(Context context,List<PictureDialogMode> mDatas) {
        mContext=context;
        this.mDatas=mDatas;
    }


    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }



    class MyViewHolderPD extends RecyclerView.ViewHolder  implements View.OnClickListener
    {

        RoundImageView picture_dialog_item_rv;
        private MyItemClickListener mListener;
        public MyViewHolderPD(View view,MyItemClickListener listener)
        {
            super(view);
            this.mListener = listener;
            picture_dialog_item_rv = (RoundImageView) view.findViewById(R.id.picture_dialog_item_rv);
            picture_dialog_item_rv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getPosition());
            }
        }
    }


}
