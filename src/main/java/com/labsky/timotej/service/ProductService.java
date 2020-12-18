package com.labsky.timotej.service;

import com.labsky.timotej.model.products.Product;

import java.util.List;
import java.util.Optional;

/**
 * @author timotej
 */
public interface ProductService {
    public List<Product> findAllByName(final List<String> basket);

    public Optional<Product> findByName(final String product);

    public List<Product> findAll();
}
