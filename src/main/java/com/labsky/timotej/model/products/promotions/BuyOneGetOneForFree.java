package com.labsky.timotej.model.products.promotions;

import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.Product;

import java.util.Collection;

/**
 * @author timotej
 */
public class BuyOneGetOneForFree implements SalePromotion {

    /**
     * The sale is computed at checkout - 50 % discount on all and double the amount of products
     * will satisfy the BOGOF requirement
     *
     * @param product
     * @param basket
     */
    @Override
    public void apply(Product product, Basket basket) {
        basket.getProducts().computeIfPresent(product, (p, c) -> c *= 2);

        if (!containsBuyOneGetOneForFreeDiscount(product.getSalePromotions())) {
            SalePromotion promotion = new BuyOneGetOneForFreeDiscount();
            product.getSalePromotions().add(promotion);
        }
    }

    private boolean containsBuyOneGetOneForFreeDiscount(Collection<SalePromotion> promotions) {
        return promotions.stream()
                .anyMatch(promotion -> promotion instanceof BuyOneGetOneForFreeDiscount);
    }

    /**
     * special discount only for BOGOF
     */
    static class BuyOneGetOneForFreeDiscount extends Discount {

        private BuyOneGetOneForFreeDiscount() {
            super(50d);
        }
    }
}
