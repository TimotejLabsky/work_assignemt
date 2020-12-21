package com.labsky.timotej.model.products.promotions;

import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.Product;

/**
 * @author timotej
 */
public interface SalePromotion {
    void apply(Product product, Basket basket);
}
