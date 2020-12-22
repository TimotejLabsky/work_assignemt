package com.labsky.timotej.service;

import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.Receipt;
import com.labsky.timotej.model.products.GenericProduct;
import com.labsky.timotej.model.products.Insurance;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.service.impl.ReceiptServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author timotej
 */
class ReceiptServiceTest {

    private static ReceiptService receiptService;

    //MOCK DATA
    private List<Product> mockProducts;
    private Basket basket;

    @BeforeAll
    public static void init() {
        receiptService = new ReceiptServiceImpl(null, null);
    }

    @BeforeEach
    public void initMock() {
        mockProducts = new ArrayList<>();
        mockProducts.add(new GenericProduct("SIM card", parseDouble("100"), "CHF", emptyList()));
        mockProducts.add(new GenericProduct("phone case", parseDouble("100"), "CHF", emptyList()));
        mockProducts.add(new GenericProduct("SIM card", parseDouble("100"), "CHF", emptyList()));

        basket = new Basket();
        basket.addAll(mockProducts);


    }


    /**
     * total calculation
     * 100 * 1.12 + 100 * 1.12 + 100 * 1.12
     */
    @Test
    void testReceiptTaxCalculation() {
        Receipt receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertEquals(336d, receipt.getTotal(), "total amount should be 300 - 3x100");
    }

    @Test
    void testReceiptTaxCalculationWithInsurance() {
        basket.add(new Insurance("phone insurance", parseDouble("100"), "CHF", emptyList()));

        Receipt receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertEquals(436d, receipt.getTotal(), "total amount should be 300 - 3x100");
    }


    @Test
    void testReceiptCount() {

        Receipt receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertNotNull(receipt.products(), "products should not be null");

        assertEquals(mockProducts.size(), receipt.products().size(), "number of products in receipt should be same as mockProducts");
        //TODO
//        receipt.products().stream()
//                .map(ProductCountPair::product)
//                .forEach(p -> {
//                    assertTrue(mockProducts.contains(p), "each product in receipt should be in mock Products");
//                });
    }
}