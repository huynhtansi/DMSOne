package com.htsi.dmsone.ui.view;

import com.htsi.dmsone.data.model.Report;

import java.util.List;

/**
 * Created by htsi.
 * Since: 10/1/16 on 12:06 PM
 * Project: DMSOne
 */

public interface ReportView extends BaseView {
    void render(List<Report> pReportList);
}
