package com.labsky.timotej;

import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.repository.ProductRepository;
import com.labsky.timotej.repository.impl.ProductRepositoryImpl;
import com.labsky.timotej.service.CashRegister;
import com.labsky.timotej.service.ProductService;
import com.labsky.timotej.service.impl.CashRegisterImpl;
import com.labsky.timotej.service.impl.ProductServiceImpl;

import java.util.List;

/**
 * @author timotej
 */
public class BigShop {

    private final ProductService productService;
    private final CashRegister cashRegister;

    public BigShop() {
        final ProductRepository productRepository = ProductRepositoryImpl.getInstance();
        this.productService = new ProductServiceImpl(productRepository);
        this.cashRegister = new CashRegisterImpl();
    }

    public static void main(String[] args) {
    }
}
