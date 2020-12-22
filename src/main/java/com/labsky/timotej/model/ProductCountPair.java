package com.labsky.timotej.model;

import com.labsky.timotej.model.products.Product;

import java.util.AbstractMap;
import java.util.Map;

/**
 * @author timotej
 */
public class ProductCountPair extends AbstractMap.SimpleEntry<Product, Integer> {

    public ProductCountPair(Product key, Integer value) {
        super(key, value);
    }

    public ProductCountPair(Map.Entry<? extends Product, ? extends Integer> entry) {
        super(entry);
    }

    public Product product() {
        return this.getKey();
    }

    public Integer count() {
        return this.getValue();
    }

    public Integer setCount(Integer value) {
        return this.setValue(value);
    }
    
}

