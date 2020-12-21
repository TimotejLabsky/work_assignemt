package com.labsky.timotej.model.products;

/**
 * @author timotej
 */
public class Insurance extends Product {

    public Insurance() {
    }

    public Insurance(String name, double price, String currency) {
        super(name, price, currency);
    }

    public static class Builder extends Product.Builder<Builder> {

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public Insurance build() {
            return new Insurance(this.name, this.price, this.currency);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
