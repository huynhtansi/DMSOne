package com.htsi.dmsone.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.htsi.dmsone.R;
import com.htsi.dmsone.di.component.AppComponent;
import com.htsi.dmsone.presenter.LoginPresenter;
import com.htsi.dmsone.ui.view.LoginView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by htsi.
 * Since: 9/22/16 on 2:19 PM
 * Project: DMSOne
 */

public class LoginFragment extends BaseFragment implements LoginView {

    @Inject
    LoginPresenter mLoginPresenter;

    @BindView(R.id.editUsername)
    EditText editUsername;

    @BindView(R.id.editPassword)
    EditText editPassword;


    @OnClick(R.id.btnLogin)
    public void onButtonLoginClicked(View pView) {

        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();


        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), R.string.warning_credentials, Toast.LENGTH_SHORT).show();
            return;
        }

        mLoginPresenter.authenticate(username, password);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    protected void onScreenVisible() {
        super.onScreenVisible();

        this.getComponent(AppComponent.class).inject(this);
        this.mLoginPresenter.setView(this);
    }

    @Override
    public void renderLoginView() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }
}
