package com.htsi.dmsone.app;

import android.util.Log;

import com.google.gson.Gson;
import com.htsi.dmsone.utils.ObscuredSharedPreferences;
import com.htsi.dmsone.utils.SecurityUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Huỳnh Phúc on 9/24/2016.
 */

public class Runtime {

    public static final String PREF_USER_LOGGEDIN = SecurityUtil.sha256("PREF_USER_LOGGEDIN");
    public static final String PREF_COOKIE = SecurityUtil.sha256("PREF_COOKIE");

    protected Gson mGson;
    protected ObscuredSharedPreferences mSharedPreferences;

    protected boolean mUserLoggedIn;
    protected Set<String> mCookies;


    public Runtime(ObscuredSharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
        mGson = new Gson();
        mUserLoggedIn = mSharedPreferences.getBoolean(PREF_USER_LOGGEDIN, false);
        mCookies = mSharedPreferences.getStringSet(PREF_COOKIE, new HashSet<String>());
    }


    public Set<String> getCookie() {
            return mCookies;
    }

    public void setCookie(Set<String> pCookies) {
        mCookies = pCookies;
        mSharedPreferences.edit().putStringSet(PREF_COOKIE, pCookies).commit();
    }

    public void setUserLoggedIn(boolean b) {
        mUserLoggedIn = b;
        mSharedPreferences.edit().putBoolean(PREF_USER_LOGGEDIN, mUserLoggedIn).commit();
    }

    public boolean getUserLoggedIn() {
        return mUserLoggedIn;
    }

    public ObscuredSharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public String getKey(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public void setKey(String key, String value) {
        mSharedPreferences.edit().putString(key, value).commit();
    }

    public void deleteKey(String key) {
        mSharedPreferences.edit().remove(key).commit();
    }


    public void logOut() {
        mUserLoggedIn = false;
        mCookies = null;

        deleteKey(PREF_USER_LOGGEDIN);
        deleteKey(PREF_COOKIE);
        Log.d("TAG", "log out");
    }

}
