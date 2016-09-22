package com.htsi.dmsone.di.module;

import android.content.Context;
import android.util.Log;

import com.htsi.dmsone.app.DMSOneApplication;
import com.htsi.dmsone.data.repository.AuthenticationRepository;
import com.htsi.dmsone.data.repository.AuthenticationRepositoryImp;
import com.htsi.dmsone.data.repository.SaleRepository;
import com.htsi.dmsone.data.repository.SaleRepositoryImp;
import com.htsi.dmsone.data.service.AuthenticationService;
import com.htsi.dmsone.data.service.SaleService;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    @Named("REST_ADAPTER")
    Retrofit provideRestAdapter() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        long t1 = System.nanoTime();
                        Log.d("OkHttpClient", String.format("Sending request %s on %s%n%s",
                                request.url(), chain.connection(), request.headers()));

                        Response response = chain.proceed(request);

                        long t2 = System.nanoTime();
                        Log.d("OkHttpClient", String.format("Received response for %s in %.1fms%n%s%d",
                                response.request().url(), (t2 - t1) / 1e6d, response.headers(), response.code()));




                        return response;
                    }
                })
                .build();
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(client)
                .addConverterFactory(GsonConverterFactory.create())
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
}
