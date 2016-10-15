package com.htsi.dmsone.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htsi.dmsone.R;
import com.htsi.dmsone.data.model.Order;
import com.htsi.dmsone.di.component.AppComponent;
import com.htsi.dmsone.presenter.ReturnProductPresenter;
import com.htsi.dmsone.ui.adapter.OrderAdapter;
import com.htsi.dmsone.ui.view.ReturnProductView;
import com.htsi.dmsone.utils.DetailsTransition;
import com.htsi.dmsone.utils.RecyclerItemClickListener;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by htsi.
 * Since: 10/7/16 on 1:49 PM
 * Project: DMSOne
 */

public class ReturnProductFragment extends BaseFragment implements ReturnProductView {

    public interface ShowOrderListener {
        void onShowOrderListener(Order pOrder, View pView);
    }

    private ShowOrderListener pListener;

    @Inject
    ReturnProductPresenter mPresenter;

    @BindView(R.id.rvListOrder)
    RecyclerView mRVListOrder;

    private OrderAdapter mAdapter;
    private String mCurrentDateString;
    private List<Order> mOrderList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_return_product, container, false);
    }


    @Override
    protected void onScreenVisible(View pView, Bundle savedInstanceState) {
        super.onScreenVisible(pView, savedInstanceState);

        getComponent(AppComponent.class).inject(this);
        mPresenter.setView(this);

        setupUI();

        Date date = new Date(System.currentTimeMillis());
        mCurrentDateString = DateFormat.format("dd/MM/yyyy", date).toString();

        if (mOrderList == null) {
            mPresenter.initialize();
            setCurrentDateString(mCurrentDateString);
        } else {
            showListOrder(mOrderList);
        }
    }


    private void setupUI() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        mRVListOrder.setLayoutManager(manager);
        mRVListOrder.setItemAnimator(new DefaultItemAnimator());
    }

    public void setCurrentDateString(String date) {
        mCurrentDateString = date;
        mAdapter = null;
        mPresenter.getListOrder(date);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        pListener = (ShowOrderListener) context;
    }

    @Override
    public void showListOrder(List<Order> pOrderList) {
        mOrderList = pOrderList;

        if (mAdapter == null)
            mAdapter = new OrderAdapter(mOrderList);


        mRVListOrder.setAdapter(mAdapter);
        mRVListOrder.addOnItemTouchListener(new RecyclerItemClickListener(getContext()) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {

                setExitTransition(new Fade());

                ((ViewGroup) holder.itemView).setTransitionGroup(false);

                View pView = holder.itemView.findViewById(R.id.orderContainer);

                OrderDetailFragment orderDetailFragment = OrderDetailFragment.newInstance(mOrderList.get(position));
                orderDetailFragment.setSharedElementEnterTransition(new DetailsTransition());
                orderDetailFragment.setEnterTransition(new Fade());
                orderDetailFragment.setSharedElementReturnTransition(new DetailsTransition());
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, orderDetailFragment, "OrderDetailFragment")
                        .addToBackStack("OrderDetailFragment")
                        .addSharedElement(pView, getString(R.string.transition_order))
                        .commit();
            }
        });
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
}
