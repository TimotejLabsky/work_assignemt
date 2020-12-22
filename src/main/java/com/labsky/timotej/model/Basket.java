package com.labsky.timotej.model;

import com.labsky.timotej.exceptions.ProductNotFoundException;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.repository.impl.ProductRepositoryImpl;
import com.labsky.timotej.service.ProductService;
import com.labsky.timotej.service.impl.ProductServiceImpl;
import com.labsky.timotej.util.ProductCountPair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author timotej
 */
public class Basket {
    private static final ProductService productService = new ProductServiceImpl(ProductRepositoryImpl.getInstance());

    private final List<ProductCountPair> products;

    public Basket() {
        this.products = new ArrayList<>();
    }

    public Basket(List<String> productNames) {
        this.products = new ArrayList<>();
        this.addAll(productService.findAllByName(productNames));
    }

    public Basket(String productsString) {
        this(getProductNames(productsString));
    }

    private static List<String> getProductNames(String productsString) {
        return Arrays.asList(productsString.split("\\r?\\n"));
    }

    public List<ProductCountPair> getProducts() {
        return products;
    }

    public List<ProductCountPair> add(Product product, int count) {
        this.products.add(new ProductCountPair(product, count));
        return this.products;
    }

    public List<ProductCountPair> add(Product product) {
        return this.add(product, 1);
    }


    public List<ProductCountPair> add(String productName, int count) throws ProductNotFoundException {
        Product product = productService.findByName(productName);
        this.add(product, count);

        return this.products;
    }

    public List<ProductCountPair> add(String productName) throws ProductNotFoundException {
        return this.add(productName, 1);
    }


    public List<ProductCountPair> addAll(Collection<Product> products) {
        products.forEach(this::add);

        return this.products;
    }

    public List<ProductCountPair> remove(Product product) {
        this.products.removeIf(keyValue -> keyValue.getKey() == product);
        return this.products;
    }

    public List<ProductCountPair> remove(String productName) throws ProductNotFoundException {
        Product product = productService.findByName(productName);

        return this.remove(product);
    }
}
