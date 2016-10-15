package com.htsi.dmsone.ui.fragment.export;

import com.htsi.dmsone.data.model.ShopProfileResponse;
import com.htsi.dmsone.data.model.Staff;
import com.htsi.dmsone.ui.fragment.BaseFragment;
import com.htsi.dmsone.ui.view.ExportReportView;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by htsi.
 * Since: 10/4/16 on 5:42 PM
 * Project: DMSOne
 */

public class BaseExportFragment extends BaseFragment implements ExportReportView {


    @Override
    public void renderReportBody(ResponseBody pResponseBody) {

    }

    @Override
    public void returnShopProfile(ShopProfileResponse pShopProfileResponse) {

    }

    @Override
    public void downloadReport(String path) {

    }

    @Override
    public void writeReportToDisk(ResponseBody pResponseBody, String path) {

    }

    @Override
    public void showHasData(boolean hasData) {

    }

    @Override
    public void showStaffList(List<Staff> pStaffList, int objectType) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }
}
