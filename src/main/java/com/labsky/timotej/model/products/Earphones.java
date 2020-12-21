package com.labsky.timotej.model.products;

/**
 * @author timotej
 */
public class Earphones extends GenericProduct {

    public Earphones() {
    }

    public Earphones(String name, Double price, String currency) {
        super(name, price, currency);
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
            return new Earphones(this.name, this.price, this.currency);
        }
    }

}