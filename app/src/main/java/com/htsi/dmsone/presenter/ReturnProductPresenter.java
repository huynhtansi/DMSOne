package com.htsi.dmsone.presenter;

import android.util.Log;

import com.htsi.dmsone.data.model.ReturnProductResponse;
import com.htsi.dmsone.data.repository.SaleRepository;
import com.htsi.dmsone.ui.view.ReturnProductView;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htsi.
 * Since: 10/7/16 on 9:33 PM
 * Project: DMSOne
 */

public class ReturnProductPresenter implements BasePresenter<ReturnProductView> {

    private ReturnProductView mReturnProductView;
    private SaleRepository mSaleRepository;

    @Inject
    ReturnProductPresenter(SaleRepository pSaleRepository) {
        mSaleRepository = pSaleRepository;
    }

    @Override
    public void setView(ReturnProductView pView) {
        mReturnProductView = pView;
    }

    public void initialize() {
        mSaleRepository.loadInfo().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getListOrder(String date) {

        mSaleRepository.listReturnProduct(date, date).enqueue(new Callback<ReturnProductResponse>() {
            @Override
            public void onResponse(Call<ReturnProductResponse> call, Response<ReturnProductResponse> response) {
                if (response.body().error == null)
                    mReturnProductView.showListOrder(response.body().orderList);
                else
                    Log.d("onResponse", "Error = " + response.body().error);
            }

            @Override
            public void onFailure(Call<ReturnProductResponse> call, Throwable t) {
                Log.d("onFailure", "Error = " + t.getMessage());

            }
        });
    }
}
