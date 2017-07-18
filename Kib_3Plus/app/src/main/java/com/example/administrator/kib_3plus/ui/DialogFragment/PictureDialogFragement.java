package com.example.administrator.kib_3plus.ui.DialogFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.administrator.kib_3plus.R;
import com.example.administrator.kib_3plus.mode.PictureDialogMode;
import com.example.administrator.kib_3plus.view.fragment.Adapter.PictureDialogAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cui on 2017/7/4.
 */

public class PictureDialogFragement extends DialogFragment {
    List<PictureDialogMode> mDatas=new ArrayList<>();
    private RecyclerView picture_dialog_re;
    private static PictureDialogFragement pictureDialogFragement;
    public static PictureDialogFragement getInstance(){
        if(pictureDialogFragement==null){
            pictureDialogFragement=new PictureDialogFragement();
        }
        return pictureDialogFragement;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.picture_dialog_fargement_include,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        initData();
        initView(v);
        return  v;
    }

    private void initData() {
    }

    private void initView(View v) {
        picture_dialog_re=(RecyclerView)v.findViewById(R.id.picture_dialog_re);
        PictureDialogAdapter pictureDialogAdapter=  new PictureDialogAdapter(getContext(),mDatas);
    }
}
