package com.labsky.timotej.model.products;

import com.labsky.timotej.model.products.constraints.HasTax;
import com.labsky.timotej.model.products.promotions.SalePromotion;

import java.util.List;

/**
 * @author timotej
 */
public class GenericProduct extends Product implements HasTax {

    public GenericProduct() {
    }

    public GenericProduct(String name, Double price, String currency, List<SalePromotion> salePromotions) {
        super(name, price, currency, salePromotions);
    }

    public static class Builder extends Product.Builder<Builder> {

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public GenericProduct build() {
            return new GenericProduct(this.name, this.price, this.currency, this.salePromotions);
        }
    }


    @Override
    public Double getTax() {
        return (this.price * Double.valueOf(TAX_RATE));
    }
}