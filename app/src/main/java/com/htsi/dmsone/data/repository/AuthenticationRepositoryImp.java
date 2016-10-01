package com.htsi.dmsone.data.repository;

import com.htsi.dmsone.data.service.AuthenticationService;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by htsi.
 * Since: 9/22/16 on 2:23 PM
 * Project: DMSOne
 */

public class AuthenticationRepositoryImp implements AuthenticationRepository {

    private AuthenticationService mAuthenticationService;

    public AuthenticationRepositoryImp(AuthenticationService pAuthenticationService) {
        mAuthenticationService = pAuthenticationService;
    }

    @Override
    public Call<ResponseBody> authenticate(String username, String password) {
        Map<String, String> credential = new HashMap<>();
        credential.put("username", username);
        credential.put("password", password);
        credential.put("lt", "");
        credential.put("_eventId", "submit");
        credential.put("submit", "Đăng nhập");
        credential.put("loginCount", "");
        //&lt=&_eventId=submit&submit=%C4%90%C4%83ng+nh%E1%BA%ADp&loginCount=
        return mAuthenticationService.authenticate(credential);
    }
}
