package com.labsky.timotej.model;

import com.labsky.timotej.exceptions.ProductNotFoundException;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.repository.impl.ProductRepositoryImpl;
import com.labsky.timotej.service.ProductService;
import com.labsky.timotej.service.impl.ProductServiceImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author timotej
 */
public class Basket {
    private static final ProductService productService = new ProductServiceImpl(ProductRepositoryImpl.getInstance());

    private final Map<Product, Integer> products;

    public Basket() {
        this.products = new HashMap<>();
    }

    public Basket(List<String> productNames) {
        this.products = new HashMap<>();
        this.addAll(productService.findAllByName(productNames));
    }

    public Basket(String productsString) {
        this(getProductNames(productsString));
    }

    //TODO get product by class | name | type ?

    private static List<String> getProductNames(String productsString) {
        return Arrays.asList(productsString.split("\\r?\\n"));
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }


    public Map<Product, Integer> add(Product product, int count) {
        //words.merge("hello", 1, Integer::sum);
        products.merge(product, count, Integer::sum);
        return this.products;
    }

    public Map<Product, Integer> add(Product product) {
        return this.add(product, 1);
    }

    public Map<Product, Integer> add(String productName, int count) throws ProductNotFoundException {
        Product product = productService.findByName(productName);
        this.add(product, count);

        return this.products;
    }

    public Map<Product, Integer> add(String productName) throws ProductNotFoundException {
        return this.add(productName, 1);
    }


    public Map<Product, Integer> addAll(Collection<Product> products) {
        products.forEach(this::add);

        return this.products;
    }


    public Map<Product, Integer> remove(Product product) {
        this.products.remove(product);
        return this.products;
    }

    public Map<Product, Integer> remove(String productName) throws ProductNotFoundException {
        Product product = productService.findByName(productName);

        return this.remove(product);
    }
}
