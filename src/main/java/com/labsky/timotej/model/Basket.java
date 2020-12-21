package com.labsky.timotej.model;

import com.labsky.timotej.exceptions.ProductNotFoundException;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.repository.ProductRepository;
import com.labsky.timotej.repository.impl.ProductRepositoryImpl;
import com.labsky.timotej.service.ProductService;
import com.labsky.timotej.service.impl.ProductServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author timotej
 */
public class Basket {
    private static final ProductService productService = new ProductServiceImpl(ProductRepositoryImpl.getInstance());

    private final List<Product> products;

    public Basket(List<String> productNames) {
        this.products = new ArrayList<>(productService.findAllByName(productNames));
    }

    public Basket(String productsString) {
        this(getProductNames(productsString));
    }

    private static List<String> getProductNames(String productsString) {
        return Arrays.asList(productsString.split("\\r?\\n"));
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Product> add(Product product) {
        this.products.add(product);
        return this.products;
    }

    public List<Product> add(String productName) throws ProductNotFoundException {
        this.products.add(productService.findByName(productName));

        return this.products;
    }

    public List<Product> remove(Product product) {
        this.products.remove(product);
        return this.products;
    }

    public List<Product> remove(String productName) throws ProductNotFoundException {
        this.products.remove(productService.findByName(productName));
        return this.products;
    }
}
