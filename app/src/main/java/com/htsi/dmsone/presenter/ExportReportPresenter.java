package com.htsi.dmsone.presenter;

import android.util.Log;

import com.htsi.dmsone.data.model.ExportFileResponse;
import com.htsi.dmsone.data.model.ShopProfileResponse;
import com.htsi.dmsone.data.repository.ReportRepository;
import com.htsi.dmsone.ui.view.ExportReportView;

import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htsi.
 * Since: 10/1/16 on 9:16 PM
 * Project: DMSOne
 */

public class ExportReportPresenter implements BasePresenter<ExportReportView> {

    private ExportReportView mExportReportView;

    private ReportRepository mReportRepository;

    @Inject
    public ExportReportPresenter(ReportRepository pReportRepository) {
        mReportRepository = pReportRepository;
    }

    @Override
    public void setView(ExportReportView pView) {
        mExportReportView = pView;
    }

    public void getReportCode(String url) {
        mReportRepository.getReportCode(url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mExportReportView.renderReportBody(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getShopProfile() {
        mReportRepository.getShopProfile().enqueue(new Callback<List<ShopProfileResponse>>() {
            @Override
            public void onResponse(Call<List<ShopProfileResponse>> call, Response<List<ShopProfileResponse>> response) {
                mExportReportView.returnShopId(response.body().get(0).id);
            }

            @Override
            public void onFailure(Call<List<ShopProfileResponse>> call, Throwable t) {
                Log.d("ExportReportPresenter", t.getMessage());
            }
        });
    }

    public void exportReportFile(String url) {
        mExportReportView.showLoading();
        mReportRepository.exportReportFile(url).enqueue(new Callback<ExportFileResponse>() {
            @Override
            public void onResponse(Call<ExportFileResponse> call, Response<ExportFileResponse> response) {
                if (response.body().hasData) {
                    mExportReportView.downloadReport(response.body().path);
                } else {
                    mExportReportView.hideLoading();
                    mExportReportView.showHasData(false);
                }
            }

            @Override
            public void onFailure(Call<ExportFileResponse> call, Throwable t) {
                Log.d("ExportReportPresenter", t.getMessage());
                mExportReportView.hideLoading();
            }
        });
    }

    public void downloadReport(final String url) {
        mReportRepository.downloadReport(url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null)
                    mExportReportView.writeReportToDisk(response.body(), url);
                mExportReportView.hideLoading();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mExportReportView.hideLoading();
            }
        });
    }
}
