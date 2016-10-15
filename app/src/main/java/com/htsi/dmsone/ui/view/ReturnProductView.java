package com.htsi.dmsone.ui.view;

import com.htsi.dmsone.data.model.Order;

import java.util.List;

/**
 * Created by htsi.
 * Since: 10/7/16 on 9:32 PM
 * Project: DMSOne
 */

public interface ReturnProductView extends BaseView {
    void showListOrder(List<Order> pOrderList);
}
