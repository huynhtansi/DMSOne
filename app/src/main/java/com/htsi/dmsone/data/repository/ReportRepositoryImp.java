package com.htsi.dmsone.data.repository;

import com.htsi.dmsone.data.model.ExportFileResponse;
import com.htsi.dmsone.data.model.Report;
import com.htsi.dmsone.data.model.ShopProfileResponse;
import com.htsi.dmsone.data.service.ReportService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Huỳnh Phúc on 9/25/2016.
 */

public class ReportRepositoryImp implements ReportRepository {

    private ReportService mReportService;

    public ReportRepositoryImp(ReportService pReportService) {
        mReportService = pReportService;
    }

    @Override
    public Call<ResponseBody> authenticate(String username, String password) {
        Map<String, String> credential = new HashMap<>();
        credential.put("username", username);
        credential.put("password", password);
        credential.put("lt", "");
        credential.put("_eventId", "submit");
        credential.put("submit", "Đăng nhập");
        credential.put("loginCount", "");
        return mReportService.authenticate(credential);
    }

    @Override
    public Call<List<Report>> getReportList() {
        return mReportService.getReportList();
    }

    @Override
    public Call<ResponseBody> getReportCode(String url) {
        return mReportService.getReportCode(url);
    }

    @Override
    public Call<List<ShopProfileResponse>> getShopProfile() {
        return mReportService.getShopProfile();
    }

    @Override
    public Call<ExportFileResponse> exportReportFile(String url) {
        return mReportService.exportReportFile(url);
    }

    @Override
    public Call<ResponseBody> downloadReport(String url) {
        return mReportService.downloadReport(url);
    }
}
