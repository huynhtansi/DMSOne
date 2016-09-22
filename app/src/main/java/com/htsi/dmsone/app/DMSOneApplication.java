package com.htsi.dmsone.app;

import android.app.Application;

import com.htsi.dmsone.di.component.AppComponent;
import com.htsi.dmsone.di.component.DaggerAppComponent;
import com.htsi.dmsone.di.module.AppModule;

/**
 * Created by htsi.
 * Since: 9/22/16 on 2:08 PM
 * Project: DMSOne
 */

public class DMSOneApplication extends Application {

    private static DMSOneApplication mInstance;
    private AppComponent mAppComponent;

    public DMSOneApplication() {
        mInstance = this;
    }

    public static DMSOneApplication getInstance() {
        return mInstance;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Dagger
        this.mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();


    }
}
