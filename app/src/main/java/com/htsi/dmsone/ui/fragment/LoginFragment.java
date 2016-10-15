package com.htsi.dmsone.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.htsi.dmsone.R;
import com.htsi.dmsone.app.DMSOneApplication;
import com.htsi.dmsone.di.component.AppComponent;
import com.htsi.dmsone.presenter.LoginPresenter;
import com.htsi.dmsone.ui.activity.MainActivity;
import com.htsi.dmsone.ui.view.LoginView;
import com.htsi.dmsone.utils.ObscuredSharedPreferences;

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

    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;

    @BindView(R.id.btnLogin)
    Button btnLogin;


    @OnClick(R.id.btnLogin)
    public void onButtonLoginClicked(View pView) {

        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();


        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), R.string.warning_invalid_credentials, Toast.LENGTH_SHORT).show();
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
    protected void onScreenVisible(View pView, Bundle savedInstanceState) {
        super.onScreenVisible(pView, savedInstanceState);

        this.getComponent(AppComponent.class).inject(this);
        this.mLoginPresenter.setView(this);

        editPassword.setText("lp50051p");
    }

    @Override
    public void performLoginSuccess() {

        ObscuredSharedPreferences preferences = ((DMSOneApplication)getContext().getApplicationContext()).getAppComponent().runtime().getSharedPreferences();
        ObscuredSharedPreferences.Editor editor = preferences.edit();
        editor.putString("Username", editUsername.getText().toString());
        editor.putString("Password", editPassword.getText().toString());
        editor.apply();

        this.startActivity(new Intent(getContext(), MainActivity.class));
    }

    @Override
    public void showLoading() {
        toggleLoading(true);
    }

    @Override
    public void hideLoading() {
        toggleLoading(false);
    }

    @Override
    public void showRetry() {
        Toast.makeText(getContext(), R.string.warning_wrong_credentials, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideRetry() {

    }

    private void toggleLoading(boolean isLoading) {
        btnLogin.setVisibility(isLoading? View.INVISIBLE:View.VISIBLE);
        btnLogin.setEnabled(!isLoading);
        pbLoading.setVisibility(isLoading? View.VISIBLE:View.GONE);
        editPassword.setEnabled(!isLoading);
        editUsername.setEnabled(!isLoading);
    }
}
