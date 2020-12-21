package com.labsky.timotej.model.products.promotions;

import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.Product;

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
        Double newPrice = product.getPrice();
        product.setPrice(newPrice * discount());
    }

    private Double discount() {
        return discountPercentage / 100;
    }
}
