package com.labsky.timotej.model.products.promotions;

import com.labsky.timotej.util.ProductCountPair;

/**
 * @author timotej
 */
public interface SalePromotion {
    void apply(ProductCountPair product);
}
