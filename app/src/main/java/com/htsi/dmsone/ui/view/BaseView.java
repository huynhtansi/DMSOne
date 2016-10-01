package com.htsi.dmsone.ui.view;

import android.content.Context;

/**
 * Created by htsi.
 * Since: 9/22/16 on 2:34 PM
 * Project: DMSOne
 */

public interface BaseView {
    void showLoading();
    void hideLoading();
    void showRetry();
    void hideRetry();
    Context getContext();
}
