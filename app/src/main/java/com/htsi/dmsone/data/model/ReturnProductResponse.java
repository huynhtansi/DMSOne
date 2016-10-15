package com.htsi.dmsone.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by htsi.
 * Since: 9/22/16 on 5:42 PM
 * Project: DMSOne
 */

public class ReturnProductResponse {

    @SerializedName("total")
    public int total;

    @SerializedName("page")
    public int page;

    @SerializedName("token")
    public String token;

    @SerializedName("errMsg")
    public String error;

    @SerializedName("rows")
    public List<Order> orderList;
}
