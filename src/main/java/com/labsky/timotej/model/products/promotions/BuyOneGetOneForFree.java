package com.labsky.timotej.model.products.promotions;

import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.Product;

import java.util.Collection;

/**
 * @author timotej
 */
public class BuyOneGetOneForFree implements SalePromotion {

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

    class BuyOneGetOneForFreeDiscount extends Discount {

        public BuyOneGetOneForFreeDiscount() {
            super(50d);
        }
    }
}
