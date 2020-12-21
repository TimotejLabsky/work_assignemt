package com.labsky.timotej.model.products.promotions;

import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.Product;

/**
 * @author timotej
 */
public class BuyOneGetOneForFree implements SalePromotion {

    @Override
    public void apply(Product product, Basket basket) {
        basket.add(product);
        product.getSalePromotions().add(new Discount(50d));
    }
}
