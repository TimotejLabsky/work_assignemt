package com.labsky.timotej.model.products;

import com.labsky.timotej.model.products.promotions.SalePromotion;

import java.util.List;

/**
 * @author timotej
 */
public class SimCard extends GenericProduct {

    public SimCard() {
    }

    public SimCard(String name, Double price, String currency, List<SalePromotion> salePromotions) {
        super(name, price, currency, salePromotions);
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
