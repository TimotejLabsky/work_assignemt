package com.labsky.timotej.service;

import com.labsky.timotej.exceptions.ProductNotFoundException;
import com.labsky.timotej.model.products.Product;

import java.util.List;

/**
 * Service layer over Product repository with handling of returned values - check for errors if necessary
 * <p>
 * Implementation is case sensitive
 *
 * @author timotej
 */
public interface ProductService {
    /**
     * Returns List of products (ignores not found one) by product's name
     *
     * @param names List of product names
     * @return List of products
     */
    List<Product> findAllByName(final List<String> names);

    /**
     * Returns Product if found by name or throws error
     *
     * @param name Product name to be found
     * @return Product if found
     * @throws ProductNotFoundException is thrown when product is not found by its name
     */
    Product findByName(final String name) throws ProductNotFoundException;

    List<Product> findAll();
}
