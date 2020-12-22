package com.labsky.timotej.model.products;

import com.labsky.timotej.model.products.promotions.SalePromotion;

import java.util.List;

/**
 * @author timotej
 */
public class Insurance extends Product {

    public Insurance() {
    }

    public Insurance(String name, Double price, String currency, List<SalePromotion> salePromotions) {
        super(name, price, currency, salePromotions);
    }

    public static class Builder extends Product.Builder<Builder> {

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public Insurance build() {
            return new Insurance(this.name, this.price, this.currency, this.salePromotions);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
