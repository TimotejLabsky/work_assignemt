package com.labsky.timotej;

import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.repository.ProductRepository;
import com.labsky.timotej.repository.impl.ProductRepositoryImpl;
import com.labsky.timotej.service.CashRegister;
import com.labsky.timotej.service.ProductService;
import com.labsky.timotej.service.impl.CashRegisterImpl;
import com.labsky.timotej.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author timotej
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class E2eTest {

    /**
     * console out capture setup
     **/
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @BeforeAll
    public static void initConsoleCapture() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    private static ProductService productService;
    private static CashRegister cashRegister;
    private static final String[] allProductsString = {"SIM card", "phone case", "phone insurance", "wired earphones", "wireless earphones"};

    private static Basket basket;


    @BeforeAll
    public static void init() {
        ProductRepository productRepository = ProductRepositoryImpl.getInstance();
        cashRegister = new CashRegisterImpl();
        productService = new ProductServiceImpl(productRepository);
    }


    @Test
    @Order(0)
    void testDependenciesInit() {
        assertNotNull(productService, "productService should no be null");
        assertNotNull(cashRegister, "cashRegister should no be null");
    }

    @Test
    @Order(1)
    void testFillBasket() {
        this.basket = new Basket();

        Collection<Product> products = productService.findAllByName(List.of(allProductsString));
        basket.addAll(products);

        assertEquals(allProductsString.length, basket.getProducts().size(),
                "all products should be in the basket");

        var productStringArray = List.of(allProductsString);
        products.forEach(p -> {
            assertTrue(productStringArray.contains(p.getName()), "all product names from should be same as in product Strings");
        });
    }

    @Test
    @Order(2)
    void testCashRegister() {
        assertNotNull(this.basket, "basket should be initialized - Test flow error");

        cashRegister.checkout(this.basket);

        assertTrue(outContent.size() > 0, "should output something");
    }

}
