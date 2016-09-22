package com.htsi.dmsone.data.service;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by htsi.
 * Since: 9/22/16 on 2:24 PM
 * Project: DMSOne
 */

public interface AuthenticationService {

    @GET("login")
    Call<ResponseBody> authenticate(@QueryMap Map<String,String> pCredential);
}
