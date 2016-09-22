package com.htsi.dmsone.data.repository;

import com.htsi.dmsone.data.model.ReturnProductResponse;

import retrofit2.Call;

/**
 * Created by htsi.
 * Since: 9/22/16 on 5:41 PM
 * Project: DMSOne
 */

public interface SaleRepository {

    Call<ReturnProductResponse> listReturnProduct(String fromDate, String toDate);
}
