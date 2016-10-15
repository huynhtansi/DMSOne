package com.htsi.dmsone.di.module;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.htsi.dmsone.R;
import com.htsi.dmsone.app.DMSOneApplication;
import com.htsi.dmsone.app.Runtime;
import com.htsi.dmsone.data.repository.AuthenticationRepository;
import com.htsi.dmsone.data.repository.AuthenticationRepositoryImp;
import com.htsi.dmsone.data.repository.ReportRepository;
import com.htsi.dmsone.data.repository.ReportRepositoryImp;
import com.htsi.dmsone.data.repository.SaleRepository;
import com.htsi.dmsone.data.repository.SaleRepositoryImp;
import com.htsi.dmsone.data.service.AuthenticationService;
import com.htsi.dmsone.data.service.ReportService;
import com.htsi.dmsone.data.service.SaleService;
import com.htsi.dmsone.utils.ObscuredSharedPreferences;

import java.net.CookieManager;
import java.net.CookiePolicy;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by htsi.
 * Since: 9/22/16 on 2:01 PM
 * Project: DMSOne
 */
@Module
public class AppModule {

    private final DMSOneApplication mDMSOneApplication;

    public AppModule(DMSOneApplication pDMSOneApplication) {
        mDMSOneApplication = pDMSOneApplication;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.mDMSOneApplication;
    }

    @Provides
    @Singleton
    ObscuredSharedPreferences provideObscuredSharedPreferences() {
        return new ObscuredSharedPreferences(this.mDMSOneApplication,
                mDMSOneApplication.getSharedPreferences(this.mDMSOneApplication.getString(R.string.app_name), Context.MODE_PRIVATE));
    }

    @Provides
    @Singleton
    Runtime provideRuntime(ObscuredSharedPreferences pSharedPreferences) {
        Log.d("TAG", "create runtime");
        return new Runtime(pSharedPreferences);
    }

    @Provides
    @Singleton
    @Named("REPORT_REST_ADAPTER")
    Retrofit provideLoginRestAdapter() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://203.190.162.26:8505/");
        return builder.build();
    }

    @Provides
    @Singleton
    @Named("REST_ADAPTER")
    Retrofit provideRestAdapter() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://203.190.162.25:8100/");
        return builder.build();
    }

    @Provides
    @Singleton
    AuthenticationService provideAuthenticationService(@Named("REST_ADAPTER") Retrofit pRetrofit) {
        return pRetrofit.create(AuthenticationService.class);
    }


    @Provides
    @Singleton
    AuthenticationRepository provideAuthenticationRepository(AuthenticationService pAuthenticationService) {
        return new AuthenticationRepositoryImp(pAuthenticationService);
    }

    @Provides
    @Singleton
    SaleService provideSaleService(@Named("REST_ADAPTER") Retrofit pRetrofit) {
        return pRetrofit.create(SaleService.class);
    }

    @Provides
    @Singleton
    SaleRepository provideSaleRepository(SaleService pSaleService) {
        return new SaleRepositoryImp(pSaleService);
    }

    @Provides
    @Singleton
    ReportService provideReportService(@Named("REPORT_REST_ADAPTER") Retrofit pRetrofit) {
        return pRetrofit.create(ReportService.class);
    }

    @Provides
    @Singleton
    ReportRepository provideReportRepository(ReportService pReportService) {
        return new ReportRepositoryImp(pReportService);
    }
}
