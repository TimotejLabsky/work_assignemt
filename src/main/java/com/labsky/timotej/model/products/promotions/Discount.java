package com.labsky.timotej.model.products.promotions;

import com.labsky.timotej.model.products.Product;

/**
 * @author timotej
 */
public class Discount implements SalePromotion {
    private final float discountPercentage;

    public Discount(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public void apply(Product product) {
        double newPrice = product.getPrice();
        product.setPrice(newPrice * discount());
    }

    private float discount() {
        return discountPercentage / 100;
    }
}
