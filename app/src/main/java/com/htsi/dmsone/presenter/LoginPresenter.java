package com.htsi.dmsone.presenter;

import android.util.Log;

import com.htsi.dmsone.data.model.SearchOrderResponse;
import com.htsi.dmsone.data.repository.AuthenticationRepository;
import com.htsi.dmsone.data.repository.SaleRepository;
import com.htsi.dmsone.ui.view.LoginView;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htsi.
 * Since: 9/22/16 on 2:33 PM
 * Project: DMSOne
 */

public class LoginPresenter implements BasePresenter<LoginView> {

    private LoginView mLoginView;
    private AuthenticationRepository mAuthenticationRepository;
    private SaleRepository mSaleRepository;

    @Inject
    public LoginPresenter(AuthenticationRepository pAuthenticationRepository, SaleRepository pSaleRepository) {
        mAuthenticationRepository = pAuthenticationRepository;
        mSaleRepository = pSaleRepository;
    }

    @Override
    public void setView(LoginView pView) {
        this.mLoginView = pView;
    }

    public void authenticate(String username, String password) {
        mLoginView.showLoading();
        this.mAuthenticationRepository.authenticate(username, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                mSaleRepository.searchOrder().enqueue(new Callback<SearchOrderResponse>() {
                    @Override
                    public void onResponse(Call<SearchOrderResponse> call, Response<SearchOrderResponse> response) {
                        mLoginView.hideLoading();
                        mLoginView.performLoginSuccess();
                        Log.d("onResponse", "Token = " + response.body().token);
                    }

                    @Override
                    public void onFailure(Call<SearchOrderResponse> call, Throwable t) {
                        Log.d("onFailure", t.getMessage());
                        mLoginView.hideLoading();
                        mLoginView.showRetry();
                    }
                });


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
