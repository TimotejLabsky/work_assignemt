package com.labsky.timotej.model.products;

/**
 * @author timotej
 */
public class SimCard extends GenericProduct {

    public SimCard() {
    }

    public SimCard(String name, double price, String currency) {
        super(name, price, currency);
    }

    public static class Builder extends Product.Builder<Builder> {

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public SimCard build() {
            return new SimCard(this.name, this.price, this.currency);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
