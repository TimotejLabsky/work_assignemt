package com.labsky.timotej.model.products.promotions;

import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.Earphones;
import com.labsky.timotej.model.products.Product;

public class InsuranceDiscount implements  SalePromotion{
    @Override
    public void apply(Product product, Basket basket) {
        if (isEarphonesInBasket(basket)){
            product.getSalePromotions().add(new InsuranceDiscountWhenEarphones());
        }

    }

    private boolean isEarphonesInBasket(Basket basket){
        return basket.getProducts().keySet().stream()
                .anyMatch(Earphones.class::isInstance);
    }

    class InsuranceDiscountWhenEarphones extends Discount{
        private static final double DISCOUNT = 20d;

        private InsuranceDiscountWhenEarphones() {
            super(DISCOUNT);
        }
    }
}
