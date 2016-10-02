package com.htsi.dmsone.di.component;

import com.htsi.dmsone.app.Runtime;
import com.htsi.dmsone.ui.activity.LoginActivity;
import com.htsi.dmsone.di.module.AppModule;
import com.htsi.dmsone.ui.activity.BaseActivity;
import com.htsi.dmsone.ui.fragment.BaseFragment;
import com.htsi.dmsone.ui.fragment.LoginFragment;
import com.htsi.dmsone.ui.fragment.ReportFragment;
import com.htsi.dmsone.ui.wizard.fragment.ExportReportFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by htsi.
 * Since: 9/22/16 on 2:12 PM
 * Project: DMSOne
 */
@Singleton
@Component( modules = {
        AppModule.class
})
public interface AppComponent {

    Runtime runtime();

    void inject(BaseActivity pActivity);
    void inject(BaseFragment pBaseFragment);
    void inject(LoginActivity pLoginActivity);
    void inject(LoginFragment pLoginFragment);
    void inject(ReportFragment pReportFragment);
    void inject(ExportReportFragment pExportReportFragment);
}
