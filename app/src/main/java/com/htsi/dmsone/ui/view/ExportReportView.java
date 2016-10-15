package com.htsi.dmsone.ui.view;

import com.htsi.dmsone.data.model.Staff;
import com.htsi.dmsone.data.model.ShopProfileResponse;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by htsi.
 * Since: 10/1/16 on 9:16 PM
 * Project: DMSOne
 */

public interface ExportReportView extends BaseView {
    void renderReportBody(ResponseBody pResponseBody);
    void returnShopProfile(ShopProfileResponse pShopProfileResponse);
    void downloadReport(String path);
    void writeReportToDisk(ResponseBody pResponseBody, String path);
    void showHasData(boolean hasData);
    void showStaffList(List<Staff> pStaffList, int objectType);
}
