package com.labsky.timotej;

import com.labsky.timotej.exceptions.ProductNotFoundException;
import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.model.products.SimCard;
import com.labsky.timotej.repository.ProductRepository;
import com.labsky.timotej.repository.impl.ProductRepositoryImpl;
import com.labsky.timotej.service.ProductService;
import com.labsky.timotej.service.ReceiptService;
import com.labsky.timotej.service.impl.ProductServiceImpl;
import com.labsky.timotej.service.impl.ReceiptServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author timotej
 */
class AssigmentSubjectsTest {

    private static ProductService productService;
    private static ReceiptService receiptService;
    private static final ProductRepository productRepository = ProductRepositoryImpl.getInstance();

    private static final Basket basket = new Basket(List.of("SIM card", "phone case", "phone insurance", "wired earphones", "wireless earphones"));

    @BeforeAll
    public static void init() {
        productService = new ProductServiceImpl(productRepository);
        receiptService = new ReceiptServiceImpl();
    }

    @Test
    void testTax() {
        Product product = null;
        try {
            product = productService.findByName("SIM card");
        } catch (ProductNotFoundException e) {
            assertTrue(true, e.getMessage());
        }

        assertNotNull(product, "product should not be empty");
        assertTrue(product instanceof SimCard, "String \"Sim card\" should represent insurance");
        assertEquals(2.4d, ((SimCard) product).getTax(), "tax should be on ");
    }

    @Test
    void testSimBOGOF() {
        assertTrue(false, "not implemented");
    }

    @Test
    void testInsuranceDiscount() {

        assertTrue(false, "not implemented");
    }

    @Test
    void testMaxNumberOfSimsInOnePurchase() {
        assertTrue(false, "not implemented");
    }
}
