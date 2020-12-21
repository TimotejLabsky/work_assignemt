package com.labsky.timotej;

import com.labsky.timotej.model.products.SimCard;
import com.labsky.timotej.service.ProductService;
import com.labsky.timotej.service.ReceiptService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author timotej
 */
class AssigmentSubjectsTest {

    private static ProductService productService;
    private static ReceiptService receiptService;

    private static final String[] basket = {"SimCard", "phone case", "phone insurance", "wired earphones", "wireless earphones"};

    @BeforeAll
    public static void init() {
        productService = null;
        receiptService = null;
    }

    @Test
    void testTax() {
        var product = productService.findByName("Sim card");

        assertTrue(product.isPresent(), "product should not be empty");
        var actualProduct = product.get();
        assertTrue(actualProduct instanceof SimCard, "String \"Sim card\" should represent insurance");
        assertEquals(actualProduct.getPrice() * 1.12, ((SimCard) actualProduct).getTax(), "tax should be on ");
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
