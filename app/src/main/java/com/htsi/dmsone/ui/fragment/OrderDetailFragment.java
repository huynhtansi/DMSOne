package com.htsi.dmsone.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.htsi.dmsone.R;
import com.htsi.dmsone.data.model.Order;
import com.htsi.dmsone.data.model.Product;
import com.htsi.dmsone.di.component.AppComponent;
import com.htsi.dmsone.presenter.OrderDetailPresenter;
import com.htsi.dmsone.ui.adapter.ProductAdapter;
import com.htsi.dmsone.ui.view.OrderDetailView;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by htsi.
 * Since: 10/8/16 on 9:42 AM
 * Project: DMSOne
 */

public class OrderDetailFragment extends BaseFragment implements OrderDetailView {

    public static final String EXTRA_ORDER_ID_KEY = "OrderKey";

    private Order mOrder;
    private int mReasonCode = -1;
    private String mToken;

    @BindView(R.id.textOrderId)
    TextView mTextOrderNumber;

    @BindView(R.id.textCustomer)
    TextView mTextCustomerName;

    @BindView(R.id.textAddress)
    TextView mTextAddress;

    @BindView(R.id.textShipper)
    TextView mTextShipper;

    @BindView(R.id.textSeller)
    TextView mTextSeller;

    @BindView(R.id.textTotal)
    TextView mTextTotal;

    @BindView(R.id.spReasons)
    TextView mSpReasons;

    @BindView(R.id.editReason)
    EditText mEditReason;

    @BindView(R.id.listProduct)
    RecyclerView mRVListProduct;

    private List<Product> mProductList;
    private List<Product> mPromoProductList;
    private ProductAdapter mAdapter;

    @Inject
    OrderDetailPresenter mPresenter;

    public static OrderDetailFragment newInstance(Order pOrder) {
        OrderDetailFragment orderDetailFragment = new OrderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_ORDER_ID_KEY, pOrder);
        orderDetailFragment.setArguments(bundle);

        return orderDetailFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrder = getArguments().getParcelable(EXTRA_ORDER_ID_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_order, container, false);
    }

    private void setupUI() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        mRVListProduct.setLayoutManager(manager);
        mRVListProduct.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onScreenVisible(View view, Bundle savedInstanceState) {
        super.onScreenVisible(view, savedInstanceState);

        if (mOrder != null) {
            mTextOrderNumber.setText(mOrder.orderNumber);
            mTextCustomerName.setText(getString(R.string.format_customer_name, mOrder.customerName, mOrder.customerCode));
            mTextAddress.setText(getString(R.string.format_customer_address, mOrder.customerAddress));
            mTextShipper.setText(getString(R.string.format_shipper, mOrder.shipper));
            mTextSeller.setText(getString(R.string.format_seller, mOrder.staffName));
            mTextTotal.setText(getString(R.string.format_total, new DecimalFormat("#,###,###").format(mOrder.total)));
        }

        setupUI();

        getComponent(AppComponent.class).inject(this);
        mPresenter.setView(this);

        if (mProductList != null && mPromoProductList != null) {
            render(mProductList, mPromoProductList, "");
        } else {
            mPresenter.getOrderDetail(mOrder);
        }
    }

    @OnClick(R.id.spReasons)
    public void chooseReason(View pView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setSingleChoiceItems(R.array.return_product_reasons, mReasonCode, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mReasonCode = which+1;
                mSpReasons.setText(getResources().getStringArray(R.array.return_product_reasons)[which]);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @OnClick(R.id.btnSubmit)
    public void onButtonSubmitClicked(View pView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.message_confirm_return_order)
        .setNegativeButton(R.string.btn_ok_title, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String reason = mEditReason.getText().toString();
                if (mToken != null && !TextUtils.isEmpty(reason) && mReasonCode > 0) {
                    Log.d("onButtonSubmitClicked", "Return order id = " + mOrder.id + "\n" +
                            "Reason = " + reason + "\n" +
                            "ReasonCode = " + mReasonCode + "\n" +
                            "Token = " + mToken);
                    //mPresenter.confirmReturnOrder(mOrder.id, reason, mReasonCode, false, mToken);
                }
            }
        })
        .setPositiveButton(R.string.btn_cancel_title, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void render(List<Product> pProductList, List<Product> pPromoProductList, String pToken) {
        mProductList = pProductList;
        mPromoProductList = pPromoProductList;
        mToken = pToken;

        if (mAdapter == null) {
            mAdapter = new ProductAdapter(mProductList, mPromoProductList);
        }

        mRVListProduct.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }
}
