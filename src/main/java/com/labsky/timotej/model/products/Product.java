package com.labsky.timotej.model.products;

/**
 * @author timotej
 */
public abstract class Product {

    protected String name;
    protected double price;
    protected String currency;

    protected Product() {
    }

    protected Product(String name, double price, String currency) {
        this.name = name;
        this.price = price;
        this.currency = currency;
    }

    public abstract static class Builder<T extends Builder<T>> {
        protected String name;
        protected double price;
        protected String currency;

        public abstract T getThis();

        public T name(String name) {
            this.name = name;
            return getThis();
        }

        public T price(double price) {
            this.price = price;
            return getThis();
        }

        public T currency(String currency) {
            this.currency = currency;
            return getThis();
        }

        public abstract Product build();
    }

    public static Builder builder() {
        return new GenericProduct.Builder();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public abstract double getFinalPrice();
}
