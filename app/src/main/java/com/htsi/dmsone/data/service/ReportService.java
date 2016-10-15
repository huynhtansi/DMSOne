package com.htsi.dmsone.data.service;

import com.htsi.dmsone.data.model.ExportFileResponse;
import com.htsi.dmsone.data.model.MapControlResponse;
import com.htsi.dmsone.data.model.Report;
import com.htsi.dmsone.data.model.StaffResponse;
import com.htsi.dmsone.data.model.ShopProfileResponse;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

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

    @GET
    Call<ResponseBody> getReportCode(@Url String url);

    @GET("rest/report/shop/kendo-ui-combobox-ho-have-shop-off/1.json")
    Call<List<ShopProfileResponse>> getShopProfile();

    @POST
    Call<ExportFileResponse> exportReportFile(@Url String url);

    @GET
    Call<ResponseBody> downloadReport(@Url String url);

    @POST
    Call<MapControlResponse> getMapControl();

    @POST("/commons/search-staff-show-list")
    Call<StaffResponse> getSellerList(@QueryMap Map<String, Integer> options);
}
