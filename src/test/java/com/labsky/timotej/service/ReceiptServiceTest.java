package com.labsky.timotej.service;

import com.labsky.timotej.model.Receipt;
import com.labsky.timotej.model.products.Earphones;
import com.labsky.timotej.model.products.GenericProduct;
import com.labsky.timotej.model.products.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author timotej
 */
class ReceiptServiceTest {

    private static ReceiptService receiptService;
    private static List<Product> mockProducts;

    @BeforeAll
    public static void init() {
        receiptService = null;

        mockProducts = new ArrayList<Product>();
        mockProducts.add(new GenericProduct("test product", 100, "CHF"));
        mockProducts.add(new GenericProduct("test product", 100, "CHF"));
        mockProducts.add(new GenericProduct("test product", 100, "CHF"));
    }

    @Test
    void testReceiptTotal() {


        Receipt receipt = receiptService.getReceipt(mockProducts);

        assertEquals(300, receipt.total(), "total amount should be 300 - 3x100");
    }

    @Test
    void testReceiptCount() {

        Receipt receipt = receiptService.getReceipt(mockProducts);

        assertNotNull(receipt.products(), "products should not be null");
        assertTrue(receipt.products().containsAll(mockProducts), "all products should be in final receipt");
    }

    @Test
    void testReceiptNotFoundException() {
        var products = mockProducts;


        Receipt receipt = receiptService.getReceipt(products);

        assertNotNull(receipt.products(), "products should not be null");
        assertTrue(receipt.products().containsAll(products), "all products should be in final receipt");

    }


}