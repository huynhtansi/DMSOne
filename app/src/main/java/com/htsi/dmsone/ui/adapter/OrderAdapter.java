package com.htsi.dmsone.ui.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.htsi.dmsone.R;
import com.htsi.dmsone.data.model.Order;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by htsi.
 * Since: 10/7/16 on 10:54 PM
 * Project: DMSOne
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> mOrderList;



    public OrderAdapter(List<Order> pOrderList) {
        mOrderList = pOrderList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_layout, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        final int adapterPosition = holder.getAdapterPosition();
        Resources res = holder.itemView.getResources();

        holder.mView.setTransitionName(res.getString(R.string.transition_order) + adapterPosition);

        Order order = mOrderList.get(adapterPosition);
        holder.mTextOrderNumber.setText(order.orderNumber);

        holder.mTextCustomerName.setText(res.getString(R.string.format_customer_name, order.customerName, order.customerCode));
        holder.mTextAddress.setText(res.getString(R.string.format_customer_address, order.customerAddress));
        holder.mTextSeller.setText(res.getString(R.string.format_seller, order.staffName));
        holder.mTextShipper.setText(res.getString(R.string.format_shipper, order.shipper));

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formatTotalString = formatter.format(order.total);
        holder.mTextTotal.setText(res.getString(R.string.format_total, formatTotalString));
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

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

        @BindView(R.id.orderContainer)
        View mView;

        OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
