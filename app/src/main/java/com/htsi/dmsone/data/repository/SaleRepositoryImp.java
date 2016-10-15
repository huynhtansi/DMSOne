package com.htsi.dmsone.data.repository;

import com.htsi.dmsone.data.model.ProductDetailResponse;
import com.htsi.dmsone.data.model.ReturnOrderResponse;
import com.htsi.dmsone.data.model.ReturnProductResponse;
import com.htsi.dmsone.data.model.SearchOrderResponse;
import com.htsi.dmsone.data.service.SaleService;

import java.util.Hashtable;
import java.util.Map;

import okhttp3.ResponseBody;
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
    public Call<ResponseBody> loadInfo() {
        return mSaleService.loadInfo();
    }

    @Override
    public Call<ReturnProductResponse> listReturnProduct(String fromDate, String toDate) {
        Map<String, String> options = new Hashtable<>();
        options.put("fromDate", fromDate);
        options.put("toDate", toDate);
        options.put("orderTypeString", "IN");
        options.put("saleOrderStatusString", "1");
        options.put("saleOrderTypeString", "1");
        options.put("flag", "true");
        return mSaleService.listReturnProduct(options);
    }

    @Override
    public Call<SearchOrderResponse> searchOrder() {
        return mSaleService.searchOrder();
    }

    @Override
    public Call<ProductDetailResponse> listProductDetail(long saleOrderId, long customerId, long staffCode, String deliveryCode, String orderNumber) {
        Map<String, String> options = new Hashtable<>();
        options.put("saleOrderId", String.valueOf(saleOrderId));
        options.put("customerId", String.valueOf(customerId));
        options.put("staffCode", String.valueOf(staffCode));
        options.put("deliveryCode", deliveryCode);
        options.put("orderNumber", orderNumber);
        return mSaleService.listProductDetail(options);
    }

    @Override
    public Call<ReturnOrderResponse> confirmReturnOrder(long saleOrderId, String reason, int reasonCode, boolean flag, String token) {
        Map<String, String> options = new Hashtable<>();
        options.put("saleOrderId", String.valueOf(saleOrderId));
        options.put("reason", String.valueOf(reason));
        options.put("reasonCode", String.valueOf(reasonCode));
        options.put("flag", String.valueOf(flag));
        options.put("token", token);
        return mSaleService.confirmReturnOrder(options);
    }
}
