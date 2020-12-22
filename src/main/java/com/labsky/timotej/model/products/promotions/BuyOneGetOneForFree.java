package com.labsky.timotej.model.products.promotions;

import com.labsky.timotej.model.ProductCountPair;

import java.util.Collection;

/**
 * @author timotej
 */
public class BuyOneGetOneForFree implements SalePromotion {

    @Override
    public void apply(ProductCountPair productCountPair) {
        productCountPair.setCount(productCountPair.count() * 2);

        if (!containsBuyOneGetOneForFreeDiscount(productCountPair.product().getSalePromotions())) {
            SalePromotion promotion = new BuyOneGetOneForFreeDiscount();
            productCountPair.product().getSalePromotions().add(promotion);
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
