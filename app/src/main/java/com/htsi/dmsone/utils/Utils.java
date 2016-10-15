package com.htsi.dmsone.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

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

    public static String join(CharSequence[] array) {
        String arrayString = "";
        for (CharSequence c:array) {
            if (TextUtils.isEmpty(c))
                continue;
            else {
                if (!TextUtils.isEmpty(arrayString))
                    arrayString += "-";
            }
            arrayString += c;
        }
        return arrayString;
    }

    public static String getValueInTag(String tag) {
        int startIndex = tag.indexOf("value=\"");
        int endIndex = tag.indexOf("/>");

        String temp = tag.substring(startIndex, endIndex);
        return temp.substring(7, temp.length() - 2);
    }

    public static void openFile(Context context, String path, String format) {
        String mineType = format.equals("PDF")? "application/pdf":"application/vnd.ms-excel";
        Intent newIntent = new Intent(Intent.ACTION_VIEW);
        newIntent.setDataAndType(Uri.fromFile(new File(path)), mineType);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(newIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No handler for this type of file.", Toast.LENGTH_LONG).show();
        }
    }

    public static String writeResponseBodyToDisk(ResponseBody body, String filename) {
        try {
            File dir = new File(Environment.getExternalStorageDirectory(), "DMSOneExport");

            if (!dir.exists())
                dir.mkdir();

            File futureStudioIconFile = new File(dir + File.separator + filename);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                }

                outputStream.flush();

                return futureStudioIconFile.getAbsolutePath();
            } catch (IOException e) {
                return null;
            } finally {
                if (inputStream != null) inputStream.close();

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static String[] getTokenReportCode(ResponseBody pResponseBody) {
        try {
            String content = pResponseBody.string();
            String[] lines = content.split(System.getProperty("line.separator"));
            String codeString = getValueInTag(lines[8]);
            String tokenString = getValueInTag(lines[10]);
            return new String[]{codeString, tokenString};
        } catch (IOException pE) {
            pE.printStackTrace();
            return null;
        }
    }

    public static void replaceFragment(AppCompatActivity pActivity, Fragment pBaseFragment, int pContainer, String tag) {
        pActivity.getSupportFragmentManager().beginTransaction().replace(pContainer, pBaseFragment, tag).commitAllowingStateLoss();
    }

}
