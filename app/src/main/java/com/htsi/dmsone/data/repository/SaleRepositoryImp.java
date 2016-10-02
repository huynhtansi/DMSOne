package com.htsi.dmsone.data.repository;

import com.htsi.dmsone.data.model.ReturnProductResponse;
import com.htsi.dmsone.data.model.SearchOrderResponse;
import com.htsi.dmsone.data.service.SaleService;

import java.util.Hashtable;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by htsi.
 * Since: 9/22/16 on 5:42 PM
 * Project: DMSOne
 */

public class SaleRepositoryImp implements SaleRepository {

    private SaleService mSaleService;

    public SaleRepositoryImp(SaleService pSaleService) {
        mSaleService = pSaleService;
    }

    @Override
    public Call<ReturnProductResponse> listReturnProduct(String fromDate, String toDate) {
        Map<String, String> options = new Hashtable<>();
        options.put("fromDate", fromDate);
        options.put("toData", toDate);
        options.put("orderTypeString", "IN");
        options.put("saleOrderStatusString", "1");
        options.put("saleOrderTypeString", "1");
        options.put("flag", "false");
        return mSaleService.listReturnProduct(options);
    }

    @Override
    public Call<SearchOrderResponse> searchOrder() {
        return mSaleService.searchOrder();
    }
}
