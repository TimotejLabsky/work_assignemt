package com.labsky.timotej.model.products;

import com.labsky.timotej.model.products.promotions.SalePromotion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author timotej
 */
public abstract class Product {

    protected String name;
    protected Double price;
    protected String currency;
    protected List<SalePromotion> salePromotions = new ArrayList<>();

    protected Product() {
    }

    protected Product(String name, Double price, String currency, List<SalePromotion> salePromotions) {
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.salePromotions = salePromotions;
    }

    public abstract static class Builder<T extends Builder<T>> {
        protected String name;
        protected Double price;
        protected String currency;
        protected List<SalePromotion> salePromotions = new ArrayList<>();

        public abstract T getThis();

        public T name(String name) {
            this.name = name;
            return getThis();
        }

        public T price(Double price) {
            this.price = price;
            return getThis();
        }

        public T currency(String currency) {
            this.currency = currency;
            return getThis();
        }

        public T salePromotions(SalePromotion salePromotion) {
            this.salePromotions.add(salePromotion);
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<SalePromotion> getSalePromotions() {
        return salePromotions;
    }

    public void setSalePromotions(List<SalePromotion> salePromotions) {
        this.salePromotions = salePromotions;
    }
}
