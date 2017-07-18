package com.example.administrator.kib_3plus.http;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.administrator.kib_3plus.Utils.GsonUtils;
import com.example.administrator.kib_3plus.Utils.LogUtils;
import com.example.administrator.kib_3plus.http.mode.AddMenberMode;

import java.io.IOException;
import java.util.List;

import cn.appscomm.db.mode.SportCacheL28TDB;
import cn.appscomm.presenter.implement.PDB;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.administrator.kib_3plus.view.fragment.AddMemberFragment.ADD_MEMBER_FAIL;
import static com.example.administrator.kib_3plus.view.fragment.AddMemberFragment.ADD_MEMBER_OK;

/**
 * Created by cui on 2017/7/17.
 */

public class UpdateDataService extends Service {

    List<SportCacheL28TDB> mSportCacheAllL28TDBs;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
        updateData();
    }
    private void updateData() {
        if(mSportCacheAllL28TDBs!=null){
            for(SportCacheL28TDB sportCacheAllL28TDB:mSportCacheAllL28TDBs){
                String json="";
                String tag="";

                OkHttpUtils.getInstance().postJsonAsynHttp(OkHttpUtils.HOST+OkHttpUtils.UPLOAD_SPORT,callback,json,tag);
            }
        }

    }
    private void initData() {
        mSportCacheAllL28TDBs=PDB.INSTANCE.getSportCacheAllL28TDB();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String sj= (String) response.body().string();//需要添加stirng强转
            LogUtils.i("SJ="+sj);


        }
    };
}
