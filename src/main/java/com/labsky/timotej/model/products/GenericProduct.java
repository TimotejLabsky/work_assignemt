package com.labsky.timotej.model.products;

import com.labsky.timotej.model.HasTax;

/**
 * @author timotej
 */
public class GenericProduct extends Product implements HasTax {

    public GenericProduct() {
    }

    public GenericProduct(String name, double price, String currency) {
        super(name, price, currency);
    }

    public static class Builder extends Product.Builder<Builder> {

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public GenericProduct build() {
            return new GenericProduct(this.name, this.price, this.currency);
        }
    }



    @Override
    public double getFinalPrice() {
        return 0 + getTax();
    }

    @Override
    public double getTax() {
        // TODO implement 12% tax
        return 0;
    }
}