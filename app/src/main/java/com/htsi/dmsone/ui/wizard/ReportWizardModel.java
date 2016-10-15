package com.htsi.dmsone.ui.wizard;

import android.content.Context;

import com.htsi.dmsone.data.model.Report;

import java.util.List;

/**
 * Created by htsi.
 * Since: 10/1/16 on 1:10 PM
 * Project: DMSOne
 */

public class ReportWizardModel extends AbstractWizardModel {


    public ReportWizardModel(Context pContext, List<Report> pReportList) {
        super(pContext,pReportList);
    }

    @Override
    protected PageList onNewRootPageList(List<Report> pReportList) {

        Report main = pReportList.get(0);

        BranchPage branchPage = new BranchPage(this, main.name, main);

        createBranchPage(main, branchPage);

        return new PageList(branchPage.setRequired(true));
    }

    private void createBranchPage(Report pReport, BranchPage main) {
        for (Report report:pReport.child) {
            if (report.attr.url != null) {
                main.addBranch(report.name, new ExportReportPage(this, report.name, report));
            } else {
                BranchPage child = new BranchPage(this, report.name, report);
                String[] choices = new String[report.child.size()];
                for (int i = 0; i < report.child.size(); i++) {
                    choices[i] = report.child.get(i).name;
                }
                child.setChoices(choices);
                main.addBranch(report.name, child);

                createBranchPage(report, child);
            }
        }
    }
}
