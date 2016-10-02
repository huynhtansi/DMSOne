package com.htsi.dmsone.ui.wizard.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.htsi.dmsone.R;
import com.htsi.dmsone.data.model.Report;
import com.htsi.dmsone.di.component.AppComponent;
import com.htsi.dmsone.presenter.ExportReportPresenter;
import com.htsi.dmsone.ui.fragment.BaseFragment;
import com.htsi.dmsone.ui.view.ExportReportView;
import com.htsi.dmsone.ui.wizard.ExportReportPage;
import com.htsi.dmsone.ui.wizard.Page;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by htsi.
 * Since: 10/1/16 on 5:24 PM
 * Project: DMSOne
 */

public class ExportReportFragment extends BaseFragment implements ExportReportView {

    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private Page mPage;
    private String mKey;

    private Report mReport;

    private int mShopId;
    private boolean mIsPrinted;
    private String mFormatFile = "XLS";
    private long mReportCode;
    private String mTokenString;

    @Inject
    ExportReportPresenter mPresenter;

    @BindView(R.id.textFromDate)
    TextView mTextFromdate;

    @BindView(R.id.textToDate)
    TextView mTextToDate;

    @BindView(R.id.textShipper)
    TextView mTextShipper;

    @BindView(R.id.textSeller)
    TextView mTextSeller;

    @BindView(R.id.rgChooseFormat)
    RadioGroup mRGChooseFormat;

    @BindView(R.id.cbPrintedTime)
    CheckBox mCBPrintedTime;

    @BindView(R.id.cbPrinted)
    CheckBox mCBPrinted;

    private ProgressDialog mPbLoading;

    public static ExportReportFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        ExportReportFragment fragment = new ExportReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ExportReportFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = mCallbacks.onGetPage(mKey);

        ExportReportPage exportReportPage = (ExportReportPage) mPage;
        mReport = exportReportPage.getReport();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mPbLoading = new ProgressDialog(getContext());
        mPbLoading.setMessage(getString(R.string.message_in_progress));
        mPbLoading.setCancelable(false);
        mPbLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        return inflater.inflate(R.layout.fragment_report_options, container, false);
    }

    @Override
    public void onAttach(Context pContext) {
        super.onAttach(pContext);

        /*if (!(pContext instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }*/

        mCallbacks = (PageFragmentCallbacks) getParentFragment();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    protected void onScreenVisible() {
        super.onScreenVisible();

        getComponent(AppComponent.class).inject(this);
        mPresenter.setView(this);

        setupUI();

        mPresenter.getReportCode(mReport.attr.url);
    }

    private void setupUI() {

        Date date = new Date(System.currentTimeMillis());
        CharSequence currentDateString = DateFormat.format("dd/MM/yyyy", date);

        mTextFromdate.setText(currentDateString);
        mTextToDate.setText(currentDateString);


        mTextFromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker pDatePicker, int pI, int pI1, int pI2) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(pI, pI1, pI2);
                        Date fromDate = new Date(calendar.getTimeInMillis());
                        mTextFromdate.setText(DateFormat.format("dd/MM/yyyy", fromDate));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                dialog.show();
            }
        });

        mTextToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker pDatePicker, int pI, int pI1, int pI2) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(pI, pI1, pI2);
                        Date fromDate = new Date(calendar.getTimeInMillis());
                        mTextToDate.setText(DateFormat.format("dd/MM/yyyy", fromDate));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                dialog.show();
            }
        });

        mRGChooseFormat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup pRadioGroup, int pI) {
                mFormatFile = pI == R.id.rbPDF? "PDF":"XLS";
            }
        });

        mCBPrinted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton pCompoundButton, boolean pB) {
                mIsPrinted = pB;
            }
        });
    }

    @OnClick(R.id.btnExport)
    public void onButtonExportClicked() {

        String temp = mReport.attr.url;
        if (temp.contains("?notApproved=1")) {
            temp = "report/daily-follow/pxhtnvgh";
        }

        String url = temp +
                "/export?shopId=" + mShopId +
                "&staffSaleCode=&deliveryCode=" +
                "&isPrint=" + (mIsPrinted? 1:0) +
                "&fromDate=" + mTextFromdate.getText() +
                "&toDate=" + mTextToDate.getText() +
                "&lstSaleOderNumberStr=" +
                "&formatType=" + mFormatFile +
                "&permissionPrint=1" +
                "&reportCode=" + mReportCode;

        mPresenter.exportReportFile(url);
    }


    @Override
    public void renderReportBody(ResponseBody pResponseBody) {

        try {
            String content = pResponseBody.string();


            String[] lines = content.split(System.getProperty("line.separator"));



            String codeString = getValueInTag(lines[8]);
            mTokenString = getValueInTag(lines[10]);

            mReportCode = Long.parseLong(codeString);

            Log.d("TEST", mTokenString + " " + mReportCode);

        } catch (IOException pE) {
            pE.printStackTrace();
        }

        mPresenter.getShopProfile();
    }

    @Override
    public void showHasData(boolean hasData) {
        Toast.makeText(getContext(), R.string.message_has_no_data, Toast.LENGTH_LONG).show();
    }

    @Override
    public void returnShopId(int pShopId) {
        mShopId = pShopId;
    }

    @Override
    public void downloadReport(String path) {
        Log.d("DownloadReport", path);
        mPresenter.downloadReport(path+"?v="+mTokenString);
    }

    @Override
    public void writeReportToDisk(ResponseBody pResponseBody, String path) {
        String fileName = path.substring(path.lastIndexOf('/'), path.indexOf("?v="));
        String fullPath = writeResponseBodyToDisk(pResponseBody, fileName);
        if (fullPath != null) {
            openFile(fullPath);
        }
    }

    @Override
    public void showLoading() {
        mPbLoading.show();
    }

    @Override
    public void hideLoading() {
        mPbLoading.dismiss();
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    public String writeResponseBodyToDisk(ResponseBody body, String filename) {
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

                    Log.d("Download", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return futureStudioIconFile.getAbsolutePath();
            } catch (IOException e) {
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

    private String getValueInTag(String tag) {
        int startIndex = tag.indexOf("value=\"");
        int endIndex = tag.indexOf("/>");

        String temp = tag.substring(startIndex, endIndex);
        return temp.substring(7, temp.length() - 2);
    }

    private void openFile(String path) {
        String mineType = mFormatFile.equals("PDF")? "application/pdf":"application/vnd.ms-excel";
        Intent newIntent = new Intent(Intent.ACTION_VIEW);
        newIntent.setDataAndType(Uri.fromFile(new File(path)), mineType);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            getContext().startActivity(newIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "No handler for this type of file.", Toast.LENGTH_LONG).show();
        }
    }

    private String fileExt(String url) {
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.contains("%")) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.contains("/")) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }

}
