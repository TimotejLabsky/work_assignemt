package com.labsky.timotej.service.impl;

import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.repository.ProductRepository;
import com.labsky.timotej.service.ProductService;

import java.util.List;
import java.util.Optional;

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
    public Optional<Product> findByName(String product) {
        return productRepository.findByName(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
