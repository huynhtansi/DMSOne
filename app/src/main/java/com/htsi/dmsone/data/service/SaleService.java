package com.htsi.dmsone.data.service;

import com.htsi.dmsone.data.model.ReturnProductResponse;
import com.htsi.dmsone.data.model.SearchOrderResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by htsi.
 * Since: 9/22/16 on 5:44 PM
 * Project: DMSOne
 */

public interface SaleService {

    @GET("sale-product/return-product")
    Call<ReturnProductResponse> listReturnProduct(@QueryMap Map<String, String> options);

    @GET("sale-product/confirm-order/searchOrder")
    Call<SearchOrderResponse> searchOrder();
}
