package com.htsi.dmsone.data.service;

import com.htsi.dmsone.data.model.Report;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by htsi.
 * Since: 10/1/16 on 11:21 AM
 * Project: DMSOne
 */

public interface ReportService {

    @GET("login")
    Call<ResponseBody> authenticate(@QueryMap Map<String,String> pCredential);

    @GET("rest/report/tree.json")
    Call<List<Report>> getReportList();

}
