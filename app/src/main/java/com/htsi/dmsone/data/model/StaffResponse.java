package com.htsi.dmsone.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by htsi.
 * Since: 10/2/16 on 9:32 PM
 * Project: DMSOne
 */

public class StaffResponse {

    @SerializedName("rows")
    public List<Staff> mStaffList;
}
