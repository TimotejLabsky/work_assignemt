package com.labsky.timotej.service.impl;

import com.labsky.timotej.exceptions.ProductNotFoundException;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.repository.ProductRepository;
import com.labsky.timotej.service.ProductService;

import java.util.List;

/**
 * @author timotej
 */
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAllByName(List<String> names) {
        return productRepository.findAllByName(names);
    }

    @Override
    public Product findByName(String name) throws ProductNotFoundException {
        return productRepository.findByName(name).orElseThrow(() -> new ProductNotFoundException("%s".formatted(name)));
    }


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
