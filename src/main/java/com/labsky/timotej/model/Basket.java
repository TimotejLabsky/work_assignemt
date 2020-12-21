package com.labsky.timotej.model;

import java.util.Arrays;
import java.util.List;

/**
 * @author timotej
 */
public class Basket {
    private final List<String> productNames;

    public Basket(List<String> productNames) {
        this.productNames = productNames;
    }

    public Basket(String productsString) {
        this.productNames = getProductNames(productsString);
    }

    private List<String> getProductNames(String productsString) {
        return Arrays.asList(productsString.split("\\r?\\n"));
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public List<String> add(String productName) {
        this.productNames.add(productName);
        return this.productNames;
    }

    public List<String> remove(String productName) {
        this.productNames.remove(productName);
        return this.productNames;
    }
}
