package com.htsi.dmsone.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by htsi.
 * Since: 10/2/16 on 8:35 AM
 * Project: DMSOne
 */

public class ShopProfileResponse {

    @SerializedName("shopId")
    public int id;

    @SerializedName("shopName")
    public String name;

    @SerializedName("shopCode")
    public String code;
}
