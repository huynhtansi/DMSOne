package com.htsi.dmsone.presenter;

import android.util.Log;

import com.htsi.dmsone.data.model.Report;
import com.htsi.dmsone.data.repository.ReportRepository;
import com.htsi.dmsone.ui.view.ReportView;

import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htsi.
 * Since: 10/1/16 on 12:05 PM
 * Project: DMSOne
 */

public class ReportPresenter implements BasePresenter<ReportView> {

    private ReportView mReportView;
    private ReportRepository mReportRepository;

    @Inject
    public ReportPresenter(ReportRepository pReportRepository) {
        mReportRepository = pReportRepository;
    }


    @Override
    public void setView(ReportView pView) {
        mReportView = pView;
    }

    public void getReportList(String username, String password) {
        mReportView.showLoading();
        mReportRepository.authenticate(username, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mReportRepository.getReportList().enqueue(new Callback<List<Report>>() {
                    @Override
                    public void onResponse(Call<List<Report>> call, Response<List<Report>> response) {
                        mReportView.hideLoading();
                        if (response.body() != null) {
                            mReportView.render(response.body());
                        } else {
                            mReportView.showRetry();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Report>> call, Throwable t) {
                        Log.d("ReportPresenter", t.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("ReportPresenter", t.getMessage());
            }
        });
    }
}
