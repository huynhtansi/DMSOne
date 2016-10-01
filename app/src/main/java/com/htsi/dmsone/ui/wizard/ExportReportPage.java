package com.htsi.dmsone.ui.wizard;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.htsi.dmsone.data.model.Report;
import com.htsi.dmsone.ui.wizard.fragment.ExportReportFragment;

import java.util.ArrayList;

/**
 * Created by htsi.
 * Since: 10/1/16 on 5:23 PM
 * Project: DMSOne
 */

public class ExportReportPage extends Page {

    protected ArrayList<String> mChoices = new ArrayList<String>();

    public ExportReportPage(ModelCallbacks callbacks, String title, Report pReport) {
        super(callbacks, title, pReport);
    }

    @Override
    public Fragment createFragment() {
        return ExportReportFragment.create(getKey());
    }

    public String getOptionAt(int position) {
        return mChoices.get(position);
    }

    public int getOptionCount() {
        return mChoices.size();
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
