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
    public List<Product> findAllByName(List<String> basket) {
        return productRepository.findAllByName(basket);
    }

    @Override
    public Product findByName(String product) throws ProductNotFoundException {
        return productRepository.findByName(product).orElseThrow(() -> new ProductNotFoundException("%s".formatted(product)));
    }


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
