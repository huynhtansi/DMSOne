package com.htsi.dmsone.ui.fragment.export;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.htsi.dmsone.R;
import com.htsi.dmsone.data.model.Report;
import com.htsi.dmsone.data.model.ShopProfileResponse;
import com.htsi.dmsone.data.model.Staff;
import com.htsi.dmsone.di.component.AppComponent;
import com.htsi.dmsone.presenter.ExportReportPresenter;
import com.htsi.dmsone.ui.fragment.BaseFragment;
import com.htsi.dmsone.ui.view.ExportReportView;
import com.htsi.dmsone.ui.wizard.ExportReportPage;
import com.htsi.dmsone.ui.wizard.Page;
import com.htsi.dmsone.ui.wizard.fragment.PageFragmentCallbacks;
import com.htsi.dmsone.utils.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

import static com.htsi.dmsone.utils.Utils.getTokenReportCode;
import static com.htsi.dmsone.utils.Utils.openFile;
import static com.htsi.dmsone.utils.Utils.writeResponseBodyToDisk;

/**
 * Created by htsi.
 * Since: 10/1/16 on 5:24 PM
 * Project: DMSOne
 */

public class ExportReportFragment extends BaseFragment implements ExportReportView {

    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;

    private Report mReport;
    private Page mPage;
    private int mShopId;

    private boolean mIsPrinted;
    private String mFormatFile = "XLS";
    private long mReportCode;
    private String mTokenString;
    private CharSequence[] mSelectedSellerCode;
    private CharSequence[] mSelectedShipperCode;


    @Inject
    ExportReportPresenter mPresenter;

    @BindView(R.id.reportOptionLayout)
    View mReportOptionLayout;

    @BindView(android.R.id.title)
    TextView mTitle;

    @BindView(R.id.editOrderNumber)
    EditText mEditOrderNumber;

    @BindView(R.id.textFromDate)
    TextView mTextFromDate;

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
        String key = args.getString(ARG_KEY);
        mPage = mCallbacks.onGetPage(key);
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

        return inflater.inflate(R.layout.fragment_export_product_by_shipper, container, false);
    }

    @Override
    public void onAttach(Context pContext) {
        super.onAttach(pContext);
        mCallbacks = (PageFragmentCallbacks) getParentFragment();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    protected void onScreenVisible(View pView, Bundle savedInstanceState) {
        super.onScreenVisible(pView, savedInstanceState);

        getComponent(AppComponent.class).inject(this);
        mPresenter.setView(this);

        setupUI();

        mPresenter.getReportCode(mReport.attr.url);
    }

    private void setupUI() {

        mTitle.setText(mPage.getTitle());

        Date date = new Date(System.currentTimeMillis());
        CharSequence currentDateString = DateFormat.format("dd/MM/yyyy", date);

        mTextFromDate.setText(currentDateString);
        mTextToDate.setText(currentDateString);


        mTextFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog dialog = new DatePickerDialog(getContext(), R.style.CalendarDialogStyle,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker pDatePicker, int pI, int pI1, int pI2) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(pI, pI1, pI2);
                        Date fromDate = new Date(calendar.getTimeInMillis());
                        mTextFromDate.setText(DateFormat.format("dd/MM/yyyy", fromDate));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                dialog.show();
            }
        });

        mTextToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog dialog = new DatePickerDialog(getContext(),R.style.CalendarDialogStyle, new DatePickerDialog.OnDateSetListener() {
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

        mTextSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mPresenter.showStaffList(mShopId, 1012);
            }
        });

        mTextShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mPresenter.showStaffList(mShopId, 4);
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
                "&fromDate=" + mTextFromDate.getText() +
                "&toDate=" + mTextToDate.getText() +
                "&lstSaleOderNumberStr=" +
                "&formatType=" + mFormatFile +
                "&permissionPrint=1" +
                "&reportCode=" + mReportCode;

        mPresenter.exportReportFile(url);
    }


    @Override
    public void renderReportBody(ResponseBody pResponseBody) {
        String[] lines = getTokenReportCode(pResponseBody);
        if (lines != null) {
            mReportCode = Long.parseLong(lines[0]);
            mTokenString = lines[1];
            mPresenter.getShopProfile();
        }
    }

    @Override
    public void showHasData(boolean hasData) {
        Snackbar.make(mReportOptionLayout, R.string.message_has_no_data, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void returnShopProfile(ShopProfileResponse pShopProfileResponse) {
        mShopId = pShopProfileResponse.id;
    }

    @Override
    public void downloadReport(String path) {
        mPresenter.downloadReport(path+"?v="+mTokenString);
    }

    @Override
    public void writeReportToDisk(ResponseBody pResponseBody, String path) {
        String fileName = path.substring(path.lastIndexOf('/'), path.indexOf("?v="));
        final String fullPath = writeResponseBodyToDisk(pResponseBody, fileName);
        if (fullPath != null)
            Snackbar.make(mReportOptionLayout, R.string.message_success_download, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.title_open, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openFile(getContext(), fullPath, mFormatFile);
                        }
                    })
                    .show();
    }

    @Override
    public void showStaffList(final List<Staff> pStaffList, final int objectType) {

        final boolean[] isCheckedList = new boolean[pStaffList.size()];

        if (objectType == 1012) {
            if (mSelectedSellerCode == null)
                mSelectedSellerCode = new CharSequence[pStaffList.size()];
            else
                for (int i = 0; i < isCheckedList.length; i++) {
                    if (!TextUtils.isEmpty(mSelectedSellerCode[i]))
                        isCheckedList[i] = true;
                }
        } else {
            if (mSelectedShipperCode == null)
                mSelectedShipperCode = new CharSequence[pStaffList.size()];
            else
                for (int i=0; i<isCheckedList.length; i++) {
                    if (!TextUtils.isEmpty(mSelectedShipperCode[i]))
                        isCheckedList[i] = true;
                }
        }

        CharSequence[] names = new CharSequence[pStaffList.size()];
        for (int i = 0; i< pStaffList.size(); i++) {
            names[i] = pStaffList.get(i).name;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(names, isCheckedList, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface pDialogInterface, int pI, boolean pB) {
                isCheckedList[pI] = pB;
            }
        });
        builder.setTitle(objectType==1012? R.string.title_seller:R.string.title_shipper);
        builder.setPositiveButton(R.string.title_OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface pDialogInterface, int pI) {
                if (objectType == 1012) {
                    for (int i=0; i<isCheckedList.length; i++) {
                        if (isCheckedList[i])
                            mSelectedSellerCode[i] = pStaffList.get(i).code;
                        else
                            mSelectedSellerCode[i] = "";
                    }
                    String codes = Utils.join(mSelectedSellerCode);
                    mTextSeller.setText(TextUtils.isEmpty(codes)?getString(R.string.empty_string):codes);
                } else {
                    for (int i=0; i<isCheckedList.length; i++) {
                        if (isCheckedList[i])
                            mSelectedShipperCode[i] = pStaffList.get(i).code;
                        else
                            mSelectedShipperCode[i] = "";
                    }
                    String codes = Utils.join(mSelectedShipperCode);
                    mTextShipper.setText(TextUtils.isEmpty(codes)?getString(R.string.empty_string):codes);
                }
            }
        });
        builder.create().show();
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
        Snackbar.make(mReportOptionLayout, R.string.message_failure_download, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void hideRetry() {

    }
}
