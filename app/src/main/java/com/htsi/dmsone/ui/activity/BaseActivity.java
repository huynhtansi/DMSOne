package com.htsi.dmsone.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.htsi.dmsone.R;
import com.htsi.dmsone.app.DMSOneApplication;
import com.htsi.dmsone.di.base.HasComponent;
import com.htsi.dmsone.di.component.AppComponent;
import com.htsi.dmsone.ui.fragment.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by htsi.
 * Since: 9/22/16 on 2:13 PM
 * Project: DMSOne
 */

public class BaseActivity extends AppCompatActivity implements HasComponent<AppComponent> {

    protected AppComponent getApplicationComponent() {
        return ((DMSOneApplication)getApplication()).getAppComponent();
    }

    protected BaseFragment hostFragment() {
        // Implement in child class
        return null;
    }

    private void addFragment(BaseFragment baseFragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tf = fm.beginTransaction();
        tf.add(R.id.container, baseFragment, baseFragment.getClass().getName());
        tf.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (hostFragment() != null) {
            setContentView(R.layout.activity_base);
        }
        if (savedInstanceState == null && hostFragment() != null) {
            addFragment(hostFragment());
        }
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }

    @Override
    public AppComponent getComponent() {
        return getApplicationComponent();
    }
}
