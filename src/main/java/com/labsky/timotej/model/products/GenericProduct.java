package com.labsky.timotej.model.products;

import com.labsky.timotej.model.products.constraints.HasTax;

/**
 * @author timotej
 */
public class GenericProduct extends Product implements HasTax {

    public GenericProduct() {
    }

    public GenericProduct(String name, Double price, String currency) {
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
    public Double getTax() {
        return (this.price * Double.valueOf(TAX_RATE));
    }
}