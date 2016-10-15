package com.htsi.dmsone.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by htsi.
 * Since: 10/15/16 on 5:53 PM
 * Project: DMSOne
 */

public class ProductDetailResponse {

    @SerializedName("token")
    public String token;

    @SerializedName("lstPromotion")
    public List<Product> mPromotionProductList;

    @SerializedName("lstDetail")
    public List<Product> mProductList;
}
