package com.labsky.timotej.service;

import com.labsky.timotej.exceptions.ProductNotFoundException;
import com.labsky.timotej.model.products.Product;

import java.util.List;

/**
 * @author timotej
 */
public interface ProductService {
    public List<Product> findAllByName(final List<String> basket);

    public Product findByName(final String product) throws ProductNotFoundException;

    public List<Product> findAll();
}
