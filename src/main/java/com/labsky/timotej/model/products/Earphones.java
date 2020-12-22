package com.labsky.timotej.model.products;

import com.labsky.timotej.model.products.promotions.SalePromotion;

import java.util.List;

/**
 * @author timotej
 */
public class Earphones extends GenericProduct {

    public Earphones() {
    }

    public Earphones(String name, Double price, String currency, List<SalePromotion> promotions) {
        super(name, price, currency, promotions);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Product.Builder<Builder> {

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public Earphones build() {
            return new Earphones(this.name, this.price, this.currency, this.salePromotions);
        }
    }

}