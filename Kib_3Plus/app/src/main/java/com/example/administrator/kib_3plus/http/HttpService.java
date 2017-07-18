package com.example.administrator.kib_3plus.http;

import cn.appscomm.server.mode.L28T.LoginL28T;
import cn.appscomm.server.mode.L28T.LoginNetL28T;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by cui on 2017/6/29.
 */

public interface  HttpService {
    @POST("sport/api/reg_for_france")
    Observable<LoginNetL28T> loginL28T(@Body LoginL28T login);

}
