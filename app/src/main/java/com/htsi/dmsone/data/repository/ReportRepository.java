package com.htsi.dmsone.data.repository;

import com.htsi.dmsone.data.model.ExportFileResponse;
import com.htsi.dmsone.data.model.Report;
import com.htsi.dmsone.data.model.ShopProfileResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Huỳnh Phúc on 9/25/2016.
 */

public interface ReportRepository {

    Call<ResponseBody> authenticate(String username, String password);

    Call<List<Report>> getReportList();

    Call<ResponseBody> getReportCode(String url);

    Call<List<ShopProfileResponse>> getShopProfile();

    Call<ExportFileResponse> exportReportFile(String url);

    Call<ResponseBody> downloadReport(String url);
}
