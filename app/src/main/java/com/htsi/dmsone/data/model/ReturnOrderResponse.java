package com.htsi.dmsone.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by htsi.
 * Since: 10/15/16 on 10:41 PM
 * Project: DMSOne
 */

public class ReturnOrderResponse {

    @SerializedName("saleOrderNumber")
    public String saleOrderNumber;

    @SerializedName("token")
    public String token;

    @SerializedName("saleOrderId")
    public long id;
}
