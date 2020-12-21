package com.labsky.timotej.repository;

import com.labsky.timotej.exceptions.ProductNotFoundException;
import com.labsky.timotej.model.products.Product;

import java.util.List;
import java.util.Optional;

/**
 * @author timotej
 */
public interface ProductRepository {
    List<Product> findAll();

    List<Product> findAllByName(List<String> names);

    Optional<Product> findByName(String name) throws ProductNotFoundException;

    Product save(Product product);
}
