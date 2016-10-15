package com.htsi.dmsone.data.service;

import com.htsi.dmsone.data.model.ProductDetailResponse;
import com.htsi.dmsone.data.model.ReturnOrderResponse;
import com.htsi.dmsone.data.model.ReturnProductResponse;
import com.htsi.dmsone.data.model.SearchOrderResponse;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by htsi.
 * Since: 9/22/16 on 5:44 PM
 * Project: DMSOne
 */

public interface SaleService {

    @GET("sale-product/return-product/info")
    Call<ResponseBody> loadInfo();

    @GET("sale-product/return-product/search")
    Call<ReturnProductResponse> listReturnProduct(@QueryMap Map<String, String> options);

    @GET("sale-product/confirm-order/searchOrder")
    Call<SearchOrderResponse> searchOrder();

    @GET("sale-product/return-product/saleorderdetail")
    Call<ProductDetailResponse> listProductDetail(@QueryMap Map<String, String> options);

    @POST("sale-product/return-product/returnproductorder")
    Call<ReturnOrderResponse> confirmReturnOrder(@QueryMap Map<String, String> options);

}
