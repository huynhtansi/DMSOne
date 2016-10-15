package com.htsi.dmsone.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by htsi.
 * Since: 10/8/16 on 3:50 PM
 * Project: DMSOne
 */

public abstract class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private GestureDetector mGestureDetector;

    protected RecyclerItemClickListener(Context context) {
        mGestureDetector = new GestureDetector(context, new GestureDetector
                .SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mGestureDetector.onTouchEvent(e)) {
            onItemClick(view.getChildViewHolder(childView), view.getChildAdapterPosition(childView));
        }
        return false;
    }

    /**
     * Called when an item has been clicked.
     *
     * @param holder The holder of the clicked view.
     * @param position The position of the clicked view.
     */
    public abstract void onItemClick(RecyclerView.ViewHolder holder, int position);
}