package com.htsi.dmsone.ui.view;

import okhttp3.ResponseBody;

/**
 * Created by htsi.
 * Since: 10/1/16 on 9:16 PM
 * Project: DMSOne
 */

public interface ExportReportView extends BaseView {
    void renderReportBody(ResponseBody pResponseBody);
    void returnShopId(int pShopId);
    void downloadReport(String path);
    void writeReportToDisk(ResponseBody pResponseBody, String path);
    void showHasData(boolean hasData);
}
