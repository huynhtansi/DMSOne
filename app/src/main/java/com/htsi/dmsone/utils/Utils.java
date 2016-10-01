package com.htsi.dmsone.utils;

import android.content.Context;

/**
 * Created by htsi.
 * Since: 10/1/16 on 1:08 PM
 * Project: DMSOne
 */

public class Utils {
    public static String getStringResource(Context pContext, String stringId) {
        int rsId = pContext.getResources().getIdentifier(stringId, "string", pContext.getPackageName());
        return rsId != 0 ? pContext.getString(rsId) : "not found";
    }
}
