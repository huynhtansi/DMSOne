package com.htsi.dmsone.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by htsi.
 * Since: 10/2/16 on 8:42 AM
 * Project: DMSOne
 */

public class MapControlResponse {

    @SerializedName("lstControl")
    List<Control> mControlList;

    public class Control {
        @SerializedName("url")
        public String url;

        @SerializedName("formCode")
        public String formCode;

        @SerializedName("formName")
        public String formName;
    }
}
