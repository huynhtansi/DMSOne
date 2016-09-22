package com.htsi.dmsone.ui.activity;

import com.htsi.dmsone.ui.fragment.BaseFragment;
import com.htsi.dmsone.ui.fragment.LoginFragment;

/**
 * Created by htsi.
 * Since: 9/22/16 on 12:24 PM
 * Project: DMSOne
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected BaseFragment hostFragment() {
        return new LoginFragment();
    }


}
