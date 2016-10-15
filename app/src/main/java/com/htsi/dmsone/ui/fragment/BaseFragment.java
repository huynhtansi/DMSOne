package com.htsi.dmsone.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.htsi.dmsone.di.base.HasComponent;

import butterknife.ButterKnife;

/**
 * Created by htsi.
 * Since: 9/22/16 on 2:13 PM
 * Project: DMSOne
 */

public class BaseFragment extends Fragment {

    protected void onScreenVisible(View view, Bundle savedInstanceState){}

    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        onScreenVisible(view, savedInstanceState);
    }
}
