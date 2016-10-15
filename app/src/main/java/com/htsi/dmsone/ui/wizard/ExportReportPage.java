package com.htsi.dmsone.ui.wizard;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.htsi.dmsone.data.model.Report;
import com.htsi.dmsone.ui.fragment.export.ExportReportFragment;
import com.htsi.dmsone.ui.fragment.export.OrderTicketVNMExportFragment;

import java.util.ArrayList;

/**
 * Created by htsi.
 * Since: 10/1/16 on 5:23 PM
 * Project: DMSOne
 */

public class ExportReportPage extends Page {

    protected ArrayList<String> mChoices = new ArrayList<>();

    ExportReportPage(ModelCallbacks callbacks, String title, Report pReport) {
        super(callbacks, title, pReport);
    }

    @Override
    public Fragment createFragment() {
        if (mReport.attr.id.equals("RPT_NPP_7_3_8")) {
            return OrderTicketVNMExportFragment.create(getKey());
        }
        return ExportReportFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem(getTitle(), mData.getString(SIMPLE_DATA_KEY), getKey()));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(SIMPLE_DATA_KEY));
    }
}
