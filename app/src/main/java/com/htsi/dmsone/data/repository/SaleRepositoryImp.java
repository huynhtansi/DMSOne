package com.htsi.dmsone.data.repository;

import com.htsi.dmsone.data.model.ReturnProductResponse;
import com.htsi.dmsone.data.service.SaleService;

import java.util.HashMap;
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
        Map options = new HashMap();
        options.put("fromDate", fromDate);
        options.put("toData", toDate);
        options.put("orderTypeString", "IN");
        options.put("saleOrderStatusString", "1");
        options.put("saleOrderTypeString", "1");
        options.put("flag", "false");
        //fromDate=22%2F09%2F2016&toDate=22%2F09%2F2016&orderTypeString=IN&saleOrderStatusString=1&saleOrderTypeString=1&flag=false
        return mSaleService.listReturnProduct(options);
    }
}
