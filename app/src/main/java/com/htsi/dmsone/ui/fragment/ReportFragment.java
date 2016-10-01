package com.htsi.dmsone.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htsi.dmsone.R;
import com.htsi.dmsone.data.model.Report;
import com.htsi.dmsone.di.component.AppComponent;
import com.htsi.dmsone.presenter.ReportPresenter;
import com.htsi.dmsone.ui.view.ReportView;
import com.htsi.dmsone.ui.wizard.AbstractWizardModel;
import com.htsi.dmsone.ui.wizard.ModelCallbacks;
import com.htsi.dmsone.ui.wizard.Page;
import com.htsi.dmsone.ui.wizard.ReportWizardModel;
import com.htsi.dmsone.ui.wizard.fragment.PageFragmentCallbacks;
import com.htsi.dmsone.utils.widget.StepPagerStrip;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by htsi.
 * Since: 10/1/16 on 12:03 PM
 * Project: DMSOne
 */

public class ReportFragment extends BaseFragment implements ReportView, ModelCallbacks,
        PageFragmentCallbacks {

    @BindView(R.id.pager)
    ViewPager mPager;

    @BindView(R.id.strip)
    StepPagerStrip mStepPagerStrip;

    @Inject
    ReportPresenter mReportPresenter;

    private AbstractWizardModel mWizardModel;

    private List<Page> mCurrentPageSequence;
    private StepPagerAdapter mPagerAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    protected void onScreenVisible() {
        super.onScreenVisible();

        getComponent(AppComponent.class).inject(this);
        mReportPresenter.setView(this);
        mReportPresenter.getReportList("lp50051", "lp50051p");
    }

    @Override
    public void render(List<Report> pReportList) {

        if (pReportList != null ) {

            mWizardModel = new ReportWizardModel(getContext(), pReportList);
            mWizardModel.registerListener(this);
            mPagerAdapter = new StepPagerAdapter(getChildFragmentManager());
            mPager.setAdapter(mPagerAdapter);

            mStepPagerStrip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
                @Override
                public void onPageStripSelected(int position) {
                    position = Math.min(mPagerAdapter.getCount() - 1, position);
                    if (mPager.getCurrentItem() != position)
                        mPager.setCurrentItem(position);
                }
            });

            mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mStepPagerStrip.setCurrentPage(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            onPageTreeChanged();
        }
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

    @Override
    public void onPageDataChanged(Page page) {
        if (page.isRequired()) {
            if (recalculateCutOffPage()) {
                mPagerAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onPageTreeChanged() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        recalculateCutOffPage();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size()); // + 1 = review step
        mPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public Page onGetPage(String key) {
        return mWizardModel.findByKey(key);
    }



    @Override
    public void onChoiceItemClick() {
        mPager.setCurrentItem(mPager.getCurrentItem()+1, true);
    }

    private boolean recalculateCutOffPage() {
        // Cut off the pager adapter at first required page that isn't completed
        int cutOffPage = mCurrentPageSequence.size() + 1;
        for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            if (page.isRequired() && !page.isCompleted()) {
                cutOffPage = i;
                break;
            }
        }

        if (mPagerAdapter.getCutOffPage() != cutOffPage) {
            mPagerAdapter.setCutOffPage(cutOffPage);
            return true;
        }

        return false;
    }

    private class StepPagerAdapter extends FragmentStatePagerAdapter {
        SparseArray<Fragment> registeredFragments = new SparseArray<>();

        private int mCutOffPage;
        private Fragment mPrimaryFragment;

        public StepPagerAdapter(FragmentManager pManager) {
            super(pManager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= mCurrentPageSequence.size()) {
                return new ReportOptionsFragment();
            }

            return mCurrentPageSequence.get(position).createFragment();
        }

        @Override
        public int getCount() {
            if (mCurrentPageSequence == null) {
                return 0;
            }
            return mCurrentPageSequence.size();
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            mPrimaryFragment = (Fragment) object;
        }

        @Override
        public int getItemPosition(Object object) {
            if (object == mPrimaryFragment) {
                // Re-use the current fragment (its position never changes)
                return POSITION_UNCHANGED;
            }

            return POSITION_NONE;
        }

        public void setCutOffPage(int cutOffPage) {
            if (cutOffPage < 0) {
                cutOffPage = Integer.MAX_VALUE;
            }
            mCutOffPage = cutOffPage;
        }

        public int getCutOffPage() {
            return mCutOffPage;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }
}
