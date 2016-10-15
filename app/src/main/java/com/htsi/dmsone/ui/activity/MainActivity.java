package com.htsi.dmsone.ui.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.DatePicker;
import android.widget.TextView;

import com.htsi.dmsone.R;
import com.htsi.dmsone.data.model.Order;
import com.htsi.dmsone.ui.fragment.ConfirmOrderFragment;
import com.htsi.dmsone.ui.fragment.ReportFragment;
import com.htsi.dmsone.ui.fragment.ReturnProductFragment;
import com.htsi.dmsone.utils.Utils;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, ReturnProductFragment.ShowOrderListener {

    @BindView(R.id.textDate)
    TextView mTextChooseDate;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private ActionBarDrawerToggle mDrawerToggle;

    private boolean mIsAnimationDrawerIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().add(R.id.container, new ReturnProductFragment(), "ReturnProductFragment").commit();

        Date date = new Date(System.currentTimeMillis());
        CharSequence currentDateString = DateFormat.format("dd/MM/yyyy", date);
        mTextChooseDate.setText(currentDateString);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    animateDrawerIcon(0, 1);
                    // Lock DrawerLayout by swiping
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                    mTextChooseDate.setVisibility(View.GONE);
                } else {
                    // Unlock DrawerLayout by swiping
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    //show hamburger
                    animateDrawerIcon(1, 0);
                    mDrawerToggle.syncState();
                    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDrawerLayout.openDrawer(GravityCompat.START);
                        }
                    });
                   mTextChooseDate.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick(R.id.textDate)
    public void onChooseDate(View pView) {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(this, R.style.CalendarDialogStyle,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker pDatePicker, int pI, int pI1, int pI2) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(pI, pI1, pI2);
                Date fromDate = new Date(calendar.getTimeInMillis());
                mTextChooseDate.setText(DateFormat.format("dd/MM/yyyy", fromDate));

                ReturnProductFragment fragment = (ReturnProductFragment)
                        getSupportFragmentManager().findFragmentByTag("ReturnProductFragment");
                fragment.setCurrentDateString(mTextChooseDate.getText().toString());

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            Log.d("TEST", "Back : " + getSupportFragmentManager().getBackStackEntryCount());
            getSupportFragmentManager().popBackStack("OrderDetailFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean isChoooseDate = false;
        if (id == R.id.nav_confirm_order) {
            Utils.replaceFragment(this, new ConfirmOrderFragment(), R.id.container, "ConfirmOrderFragment");
            isChoooseDate = false;
        } else if (id == R.id.nav_return_product) {
            isChoooseDate = true;
            Utils.replaceFragment(this, new ReturnProductFragment(), R.id.container, "ReturnProductFragment");
        } else if (id == R.id.nav_report) {
            isChoooseDate = false;
            Utils.replaceFragment(this, new ReportFragment(), R.id.container, "ReportFragment");
        }

        mTextChooseDate.setVisibility(isChoooseDate?View.VISIBLE:View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onShowOrderListener(Order pOrder, View pView) {

    }

    public void animateDrawerIcon(float start, float end) {
        ValueAnimator anim = ValueAnimator.ofFloat(start, end);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                mDrawerToggle.onDrawerSlide(mDrawerLayout, slideOffset);
            }
        });
        anim.setInterpolator(new DecelerateInterpolator());
        // You can change this duration to more closely match that of the default animation.
        mIsAnimationDrawerIcon = true;
        anim.setDuration(400);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnimationDrawerIcon = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }
}
