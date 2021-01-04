package com.labsky.timotej;

import com.labsky.timotej.exceptions.ProductNotFoundException;
import com.labsky.timotej.model.Basket;
import com.labsky.timotej.repository.ProductRepository;
import com.labsky.timotej.repository.impl.ProductRepositoryImpl;
import com.labsky.timotej.service.CashRegister;
import com.labsky.timotej.service.ProductService;
import com.labsky.timotej.service.impl.CashRegisterImpl;
import com.labsky.timotej.service.impl.ProductServiceImpl;

import java.util.Arrays;

import static java.lang.System.err;

/**
 * @author timotej
 */
public class BigShop {
    public static final String INTERACTIVE_MODE = "-i";

    private final ProductService productService;
    private final CashRegister cashRegister;

    public BigShop() {
        final ProductRepository productRepository = ProductRepositoryImpl.getInstance();
        this.productService = new ProductServiceImpl(productRepository);
        this.cashRegister = new CashRegisterImpl();
    }

    public static void main(String[] args) {
        BigShop bigShop = new BigShop();

        if (Arrays.asList(args).contains(INTERACTIVE_MODE)) {
            throw new UnsupportedOperationException("this might be implemented in future");
        } else {
            bigShop.nonInteractive(args);

        }
    }

    private void nonInteractive(String[] products) {
        Basket basket = new Basket();

        for (String product : products) {
            try {
                basket.add(product);
            } catch (ProductNotFoundException e) {
                err.printf("Error: %s", e.getMessage());
                return;
            }
        }

        cashRegister.checkout(basket);

    }
}
