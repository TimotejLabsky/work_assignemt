package com.labsky.timotej.model.products.promotions;

import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.Product;

import java.math.BigDecimal;

/**
 * @author timotej
 */
public class Discount implements SalePromotion {
    private final Double discountPercentage;

    public Discount(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public void apply(Product product, Basket basket) {
        product.setPrice(product.getPrice().multiply(discount()));
    }

    private BigDecimal discount() {
        return BigDecimal.valueOf(1 - discountPercentage / 100);
    }

}
