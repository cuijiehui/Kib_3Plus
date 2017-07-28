package com.example.administrator.kib_3plus.view.fragment.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.mode.ChoresListMode;
import com.example.administrator.kib_3plus.mode.RewardsListMode;
import com.example.administrator.kib_3plus.ui.SlidingButtonView;

import java.util.ArrayList;
import java.util.List;

import cn.appscomm.db.mode.ChoresL28TDB;
import cn.appscomm.db.mode.RewardsL28TDB;

/**
 * Created by cui on 2017/7/14.
 */

public class RewardsRecyclerAdapter extends RecyclerView.Adapter<RewardsRecyclerAdapter.MyViewHolder> implements SlidingButtonView.IonSlidingButtonListener {

    private static String TAG="RewardsRecyclerAdapter";
    private Context mContext;
    private IonSlidingViewClickListener mIDeleteBtnClickListener;
    private List<RewardsListMode> mDatas = new ArrayList<RewardsListMode>();
    private SlidingButtonView mMenu = null;
    List<RewardsL28TDB> mRewardsL28TDBs;
    public RewardsRecyclerAdapter(Context context, IonSlidingViewClickListener ionSlidingViewClickListener
            , List<RewardsListMode> data, List<RewardsL28TDB> rewardsL28TDBs) {
        mContext = context;
        mIDeleteBtnClickListener = ionSlidingViewClickListener;
        mDatas=data;
        mRewardsL28TDBs=rewardsL28TDBs;
    }
    public void updateData( List<RewardsListMode> mDatas){
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        LogUtils.i(TAG,"position"+position);
        RewardsListMode rewardsListMode= mDatas.get(position);
        holder.textView.setText(rewardsListMode.getName());        //设置内容布局的宽为屏幕宽度
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        holder.layout_content.getLayoutParams().width = wm.getDefaultDisplay().getWidth();
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                } else {
                    int n = holder.getLayoutPosition();
                    mIDeleteBtnClickListener.onItemClick(v, n);
                }
            }
        });
        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = holder.getLayoutPosition();
                mIDeleteBtnClickListener.onDeleteBtnCilck(v, n);
            }
        });
        if(mRewardsL28TDBs==null||mRewardsL28TDBs.size()<=0){
            holder.c_r_item_date_ll.setVisibility(View.GONE);
        }else{
            LogUtils.i(TAG,"mChoresL28TDBs.size"+mRewardsL28TDBs.size());
            for(RewardsL28TDB rewardsL28TDB:mRewardsL28TDBs){
                if(rewardsL28TDB.getChoresId()==rewardsListMode.getChoresId()){
                    LogUtils.i(TAG,"111111111111111111111111111"+rewardsL28TDB.toString());
                    holder.c_r_item_date_ll.setVisibility(View.VISIBLE);
                    holder.c_r_item_gold_tv.setText(" x"+rewardsL28TDB.getGold()+"");
                    holder.c_r_item_date_tv.setText(getDateString(rewardsL28TDB.getDate()));

                }

            }
        }

    }

    private String getDateString(String date){
        StringBuffer dateS=new StringBuffer();
        if (date.equals(null) || date.equals("")|| date.equals("0000000")) {
            dateS.append("");
            return dateS.toString();
        }
        if (date.equals("1111111") ) {
            dateS.append("Daily");
            return dateS.toString();
        }
        dateS.append("Weekly:");
        String weeks1 = date.trim().substring(0,1);
        String weeks2 = date.trim().substring(1,2);
        String weeks3 = date.trim().substring(2,3);
        String weeks4 = date.trim().substring(3,4);
        String weeks5 = date.trim().substring(4,5);
        String weeks6 = date.trim().substring(5,6);
        String weeks7 = date.trim().substring(6,7);
        LogUtils.i("","weeks1="+weeks1);
        LogUtils.i("","weeks2="+weeks2);
        LogUtils.i("","weeks3="+weeks3);
        LogUtils.i("","weeks4="+weeks4);
        LogUtils.i("","weeks5="+weeks5);
        LogUtils.i("","weeks6="+weeks6);
        LogUtils.i("","weeks7="+weeks7);
        if (weeks7.equals("1")) {
            dateS.append(" Sun");
        }
        if (weeks6.equals("1")) {
            dateS.append(" Sat");

        }
        if (weeks5.equals("1")) {
            dateS.append(" Fri");

        }
        if (weeks4.equals("1")) {
            dateS.append(" Thu");

        }
        if (weeks3.equals("1")) {
            dateS.append(" Wed");

        }
        if (weeks2 .equals("1")) {
            dateS.append(" Tue");

        }
        if (weeks1 .equals("1")) {
            dateS.append(" Mon");
        }

        return dateS.toString();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.c_r_list_item, arg0,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    public void removeData(int position){
//        mDatas.remove(position);
        notifyItemRemoved(position);
    }
    /**     * 删除菜单打开信息接收     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }
    /**
     * 滑动或者点击了Item监听
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if(menuIsOpen()){
            if(mMenu != slidingButtonView){
                closeMenu();
            }
        }
    }
    /**     * 关闭菜单     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;
    }
    /**     * 判断是否有菜单打开     */
    public Boolean menuIsOpen() {
        if(mMenu != null){
            return true;
        }
        return false;
    }
    public interface IonSlidingViewClickListener {
        void onItemClick(View view, int position);
        void onDeleteBtnCilck(View view, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView btn_Delete;
        public TextView textView,c_r_item_gold_tv,c_r_item_date_tv;
        public  LinearLayout c_r_item_date_ll;
        public ViewGroup layout_content;
        public MyViewHolder(View itemView) {
            super(itemView);
            btn_Delete = (ImageView) itemView.findViewById(R.id.c_r_item_delete_iv);
            textView = (TextView) itemView.findViewById(R.id.c_r_item_text_tv);
            c_r_item_gold_tv = (TextView) itemView.findViewById(R.id.c_r_item_gold_tv);
            c_r_item_date_tv = (TextView) itemView.findViewById(R.id.c_r_item_date_tv);
            c_r_item_date_ll = (LinearLayout) itemView.findViewById(R.id.c_r_item_data_ll);
            layout_content = (ViewGroup) itemView.findViewById(R.id.layout_content);
            ((SlidingButtonView) itemView).setSlidingButtonListener(RewardsRecyclerAdapter.this);        }    }
}
