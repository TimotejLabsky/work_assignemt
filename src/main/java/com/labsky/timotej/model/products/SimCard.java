package com.labsky.timotej.model.products;

import com.labsky.timotej.exceptions.SimCardCountRestrictionException;
import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.constraints.Constraint;
import com.labsky.timotej.model.products.promotions.SalePromotion;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author timotej
 */
public class SimCard extends GenericProduct implements Constraint {
    public static final int MAX_NUMBER_IN_BASKET = 10;

    public SimCard() {
    }

    public SimCard(String name, BigDecimal price, String currency, List<SalePromotion> salePromotions) {
        super(name, price, currency, salePromotions);
    }

    @Override
    public boolean isValid(Basket basket) throws SimCardCountRestrictionException {
        if (basket.getProducts().get(this) > MAX_NUMBER_IN_BASKET) {
            throw new SimCardCountRestrictionException("number of sim is higher than allowed %d > %d".formatted(basket.getProducts().get(this), MAX_NUMBER_IN_BASKET));
        }

        return true;
    }

    public static class Builder extends Product.Builder<Builder> {

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public SimCard build() {
            return new SimCard(this.name, this.price, this.currency, this.salePromotions);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
