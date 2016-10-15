package com.htsi.dmsone.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by htsi.
 * Since: 10/15/16 on 5:45 PM
 * Project: DMSOne
 */

public class Product {

    @SerializedName("programCode")
    public String programCode;

    @SerializedName("price")
    public int price;

    @SerializedName("quantity")
    public int quantity;

    @SerializedName("productCode")
    public String productCode;

    @SerializedName("productName")
    public String productName;

    @SerializedName("warehouseName")
    public String warehouseName;
}
