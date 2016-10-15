package com.htsi.dmsone.presenter;

import com.htsi.dmsone.data.model.Order;
import com.htsi.dmsone.data.model.ProductDetailResponse;
import com.htsi.dmsone.data.model.ReturnOrderResponse;
import com.htsi.dmsone.data.repository.SaleRepository;
import com.htsi.dmsone.ui.view.OrderDetailView;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htsi.
 * Since: 10/15/16 on 7:38 PM
 * Project: DMSOne
 */

public class OrderDetailPresenter implements BasePresenter<OrderDetailView> {

    private OrderDetailView mOrderDetailView;
    private SaleRepository mSaleRepository;

    @Inject
    OrderDetailPresenter(SaleRepository pSaleRepository) {
        mSaleRepository = pSaleRepository;
    }

    @Override
    public void setView(OrderDetailView pView) {
        mOrderDetailView = pView;
    }

    public void getOrderDetail(Order pOrder) {
        mSaleRepository.listProductDetail(pOrder.id, pOrder.customerId, pOrder.staffCode, pOrder.shipper, pOrder.orderNumber)
                .enqueue(new Callback<ProductDetailResponse>() {
                    @Override
                    public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                        if (response.body() != null) {
                            mOrderDetailView.render(response.body().mProductList, response.body().mPromotionProductList, response.body().token);
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDetailResponse> call, Throwable t) {

                    }
                });
    }


    public void confirmReturnOrder(long id, String reason, int reasonCode, boolean flag, String token) {
        mSaleRepository.confirmReturnOrder(id, reason, reasonCode, flag, token)
                .enqueue(new Callback<ReturnOrderResponse>() {
                    @Override
                    public void onResponse(Call<ReturnOrderResponse> call, Response<ReturnOrderResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<ReturnOrderResponse> call, Throwable t) {

                    }
                });
    }
}
