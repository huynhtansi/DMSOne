package com.htsi.dmsone.data.repository;

import com.htsi.dmsone.data.model.ProductDetailResponse;
import com.htsi.dmsone.data.model.ReturnOrderResponse;
import com.htsi.dmsone.data.model.ReturnProductResponse;
import com.htsi.dmsone.data.model.SearchOrderResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by htsi.
 * Since: 9/22/16 on 5:41 PM
 * Project: DMSOne
 */

public interface SaleRepository {

    Call<ResponseBody> loadInfo();

    Call<ReturnProductResponse> listReturnProduct(String fromDate, String toDate);

    Call<SearchOrderResponse> searchOrder();

    Call<ProductDetailResponse> listProductDetail(long saleOrderId, long customerId, long staffCode, String deliveryCode, String orderNumber);

    Call<ReturnOrderResponse> confirmReturnOrder(long saleOrderId, String reason, int reasonCode, boolean flag, String token);
}
