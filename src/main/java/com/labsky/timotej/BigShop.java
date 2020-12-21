package com.labsky.timotej;

import com.labsky.timotej.repository.ProductRepository;
import com.labsky.timotej.repository.impl.ProductRepositoryImpl;

/**
 * @author timotej
 */
public class BigShop {

    public static void main(String[] args) {
        ProductRepository productRepository = ProductRepositoryImpl.getInstance();
    }
}
