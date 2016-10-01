package com.htsi.dmsone.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by htsi.
 * Since: 10/1/16 on 11:31 AM
 * Project: DMSOne
 */

public class Report {

    @SerializedName("attr")
    public Attribute attr;

    @SerializedName("data")
    public String name;

    @SerializedName("children")
    public List<Report> child;

    public class Attribute {
        @SerializedName("url")
        public String url;

        @SerializedName("name")
        public String name;

        @SerializedName("id")
        public String id;
    }
}
