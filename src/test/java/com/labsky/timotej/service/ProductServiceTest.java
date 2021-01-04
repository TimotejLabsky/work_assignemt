package com.labsky.timotej.service;

import com.labsky.timotej.exceptions.ProductNotFoundException;
import com.labsky.timotej.model.products.Earphones;
import com.labsky.timotej.repository.ProductRepository;
import com.labsky.timotej.repository.impl.ProductRepositoryImpl;
import com.labsky.timotej.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author timotej
 */
class ProductServiceTest {

    private static ProductService productService;
    private static final int NUMBER_OF_PRODUCTS_IN_WAREHOUSE = 5;

    @BeforeAll
    public static void init() {
        ProductRepository productRepository = ProductRepositoryImpl.getInstance();
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void testFindAll() {
        var products = productService.findAll();

        assertNotNull(products, "products should return at least empty array");
        assertEquals(NUMBER_OF_PRODUCTS_IN_WAREHOUSE, products.size(),
                "number of products in warehouse is not equal with loaded data");
    }

    @Test
    void testFindAllByName() {
        final int numberOfProductsInBasket = 2;
        final String basket = """
                SIM card
                phone case
                """;

        var products = productService.findAllByName(getListOfProductStrings(basket));

        assertNotNull(products, "products should not be null after findAllByName if no error");
        assertEquals(numberOfProductsInBasket, products.size(),
                "size of products array should be same as number defined in test");
    }

    @Test
    void testFindAllByNameNotCorrectName() {
        final int numberOfCorrect = 1;
        final String basket = """
                SIM Card
                phone case
                 """;
        var products = productService.findAllByName(getListOfProductStrings(basket));

        assertNotNull(products, "products should not be null after findAllByName if no error");
        assertEquals(numberOfCorrect, products.size(),
                "size of products array should be same as number defined in test");
    }

    @Test
    void testFindByName() {
        final String productNameToFind = "wired earphones";

        var product = assertDoesNotThrow(() -> productService.findByName(productNameToFind));
        assertNotNull(product, "products should not be NULL");

        assertEquals(productNameToFind, product.getName(),
                "name defined in test should be same as product name from method");
        assertEquals(Earphones.class, product.getClass(),
                "product should be correct class");
    }

    @Test
    void testThrowProductNotFoundException() {
        final String productNameToFind = "no earphones";

        var product = assertThrows(ProductNotFoundException.class, () -> productService.findByName(productNameToFind));
        assertNotNull(product, "exceptions should not be NULL");
    }

    private List<String> getListOfProductStrings(String productsString) {
        return Arrays.asList(productsString.split("\\r?\\n"));
    }

}