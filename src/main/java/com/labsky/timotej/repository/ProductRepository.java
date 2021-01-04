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

    /**
     * Returns List of products (ignores not found one) by product's name
     *
     * @param names List of product names
     * @return List of products
     */
    List<Product> findAllByName(List<String> names);

    /**
     * Returns Optional of Product - handling of not found needs to be on higher level
     *
     * @param name Product name to be found
     * @return Optional of product - empty if not found
     */
    Optional<Product> findByName(String name);
}
