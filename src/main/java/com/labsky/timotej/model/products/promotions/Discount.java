package com.labsky.timotej.model.products.promotions;

import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.util.ProductCountPair;

/**
 * @author timotej
 */
public class Discount implements SalePromotion {
    private final Double discountPercentage;

    public Discount(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public void apply(ProductCountPair productCountPair) {
        Product product = productCountPair.product();
        
        Double newPrice = product.getPrice();
        product.setPrice(newPrice * discount());
    }

    private Double discount() {
        return discountPercentage / 100;
    }
}
