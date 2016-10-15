package com.htsi.dmsone.ui.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.htsi.dmsone.R;
import com.htsi.dmsone.data.model.Product;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by htsi.
 * Since: 10/15/16 on 7:57 PM
 * Project: DMSOne
 */

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> mProductList;
    private  List<Product> mPromoProductList;

    public ProductAdapter(List<Product> pProductList, List<Product> pPromoProductList) {
        mProductList = pProductList;
        mPromoProductList = pPromoProductList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.item_product_layout, parent, false);
                return new ProductViewHolder(view);
            case 1:
                view = inflater.inflate(R.layout.item_promo_product_layout, parent, false);
                return new PromoProductViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Resources res = holder.itemView.getResources();
        Product product;

        if (getItemViewType(position) == 0) {

            product = mProductList.get(position);

            ProductViewHolder viewHolder = (ProductViewHolder)holder;
            viewHolder.mTextProductName.setText(product.productName);
            viewHolder.mTextPrice.setText(res.getString(R.string.format_price, product.price));
            viewHolder.mTextPromoCode.setText(res.getString(R.string.format_promo_code, product.programCode));
            viewHolder.mTextQunatity.setText(res.getString(R.string.format_quantity, product.quantity));
            viewHolder.mTextWareHouse.setText(res.getString(R.string.format_ware_house, product.warehouseName));

            int total = product.price*product.quantity;
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String formatTotalString = formatter.format(total);
            viewHolder.mTextTotal.setText(res.getString(R.string.format_total, formatTotalString));

        } else {

            product = mPromoProductList.get(position-mProductList.size());
            PromoProductViewHolder viewHolder = (PromoProductViewHolder)holder;
            viewHolder.mTextProductName.setText(product.productName);
            viewHolder.mTextWareHouse.setText(res.getString(R.string.format_ware_house, product.warehouseName));
            viewHolder.mTextPromoCode.setText(res.getString(R.string.format_promo_code, product.programCode));
            viewHolder.mTextQuantity.setText(res.getString(R.string.format_quantity, product.quantity));
        }
    }

    @Override
    public int getItemCount() {
        return mProductList.size() + mPromoProductList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position < mProductList.size()? 0:1;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textProductName)
        TextView mTextProductName;

        @BindView(R.id.textPrice)
        TextView mTextPrice;

        @BindView(R.id.textWareHouse)
        TextView mTextWareHouse;

        @BindView(R.id.textQuantity)
        TextView mTextQunatity;

        @BindView(R.id.textPromoCode)
        TextView mTextPromoCode;

        @BindView(R.id.textTotal)
        TextView mTextTotal;

        ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class PromoProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textProductName)
        TextView mTextProductName;

        @BindView(R.id.textWareHouse)
        TextView mTextWareHouse;

        @BindView(R.id.textPromoCode)
        TextView mTextPromoCode;

        @BindView(R.id.textQuantity)
        TextView mTextQuantity;

        PromoProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
