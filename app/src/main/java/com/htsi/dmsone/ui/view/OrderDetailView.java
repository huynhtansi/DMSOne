package com.htsi.dmsone.ui.view;

import com.htsi.dmsone.data.model.Product;

import java.util.List;

/**
 * Created by htsi.
 * Since: 10/15/16 on 7:35 PM
 * Project: DMSOne
 */

public interface OrderDetailView extends BaseView {
    void render(List<Product> pProductList, List<Product> pPromotionPProductList, String token);
}
