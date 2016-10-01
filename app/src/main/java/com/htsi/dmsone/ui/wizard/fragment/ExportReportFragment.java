package com.htsi.dmsone.ui.wizard.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.htsi.dmsone.R;
import com.htsi.dmsone.data.model.Report;
import com.htsi.dmsone.ui.fragment.BaseFragment;
import com.htsi.dmsone.ui.wizard.ExportReportPage;
import com.htsi.dmsone.ui.wizard.Page;

import butterknife.BindView;

/**
 * Created by htsi.
 * Since: 10/1/16 on 5:24 PM
 * Project: DMSOne
 */

public class ExportReportFragment extends BaseFragment {

    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private Page mPage;
    private String mKey;

    private Report mReport;

    @BindView(R.id.textId)
    TextView mTextId;

    public static ExportReportFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        ExportReportFragment fragment = new ExportReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ExportReportFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = mCallbacks.onGetPage(mKey);

        ExportReportPage exportReportPage = (ExportReportPage) mPage;
        mReport = exportReportPage.getReport();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_options, container, false);
    }

    @Override
    public void onAttach(Context pContext) {
        super.onAttach(pContext);

        /*if (!(pContext instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }*/

        mCallbacks = (PageFragmentCallbacks) getParentFragment();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    protected void onScreenVisible() {
        super.onScreenVisible();

        mTextId.setText(mReport.attr.url);
    }
}
