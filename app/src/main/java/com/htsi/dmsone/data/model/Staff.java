package com.htsi.dmsone.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by htsi.
 * Since: 10/2/16 on 9:37 PM
 * Project: DMSOne
 */

public class Staff {

    @SerializedName("staffCode")
    public String code;

    @SerializedName("staffName")
    public String name;
}
