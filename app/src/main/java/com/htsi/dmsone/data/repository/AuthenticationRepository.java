package com.htsi.dmsone.data.repository;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by htsi.
 * Since: 9/22/16 on 2:22 PM
 * Prject: DMSOne
 */

public interface AuthenticationRepository {
    Call<ResponseBody> authenticate(String username, String password);
}
