package com.labsky.timotej.model.products.promotions;

import com.labsky.timotej.util.ProductCountPair;

import java.util.Collection;

/**
 * @author timotej
 */
public class BuyOneGetOneForFree implements SalePromotion {

    @Override
    public void apply(ProductCountPair productCountPair) {
        productCountPair.increaseCount();

        if (!containsBuyOneGetOneForFreeDiscount(productCountPair.product().getSalePromotions())) {
            SalePromotion promotion = new BuyOneGetOneForFreeDiscount();

            productCountPair.product().getSalePromotions().add(promotion);
            promotion.apply(productCountPair);
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
