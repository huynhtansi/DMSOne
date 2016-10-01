package com.htsi.dmsone.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htsi.dmsone.R;

/**
 * Created by htsi.
 * Since: 10/1/16 on 4:02 PM
 * Project: DMSOne
 */

public class ReportOptionsFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_options, container, false);
    }
}
