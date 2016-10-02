package com.htsi.dmsone.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by htsi.
 * Since: 10/2/16 on 1:50 PM
 * Project: DMSOne
 */

public class ExportFileResponse {

    @SerializedName("hasData")
    public boolean hasData;

    @SerializedName("path")
    public String path;
}
