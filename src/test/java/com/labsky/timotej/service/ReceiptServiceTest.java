package com.labsky.timotej.service;

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
        receiptService = new ReceiptServiceImpl();
    }

    @BeforeEach
    public void initMock(){
        mockProducts = new ArrayList<>();
        mockProducts.add(new GenericProduct("SIM card", parseDouble("100"), "CHF"));
        mockProducts.add(new GenericProduct("phone case", parseDouble("100"), "CHF"));
        mockProducts.add(new GenericProduct("SIM card", parseDouble("100"), "CHF"));
    }


    /**
     * total calculation
     * 100 * 1.12 + 100 * 1.12 + 100 * 1.12
     */
    @Test
    void testReceiptTaxCalculation() {
        Receipt receipt = receiptService.getReceipt(mockProducts);

        assertEquals(336d, receipt.getTotal(), "total amount should be 300 - 3x100");
    }

    @Test
    void testReceiptTaxCalculationWithInsurance() {
        mockProducts.add(new Insurance("phone insurance", parseDouble("100"), "CHF"));
        Receipt receipt = receiptService.getReceipt(mockProducts);

        assertEquals(436d, receipt.getTotal(), "total amount should be 300 - 3x100");
    }


    @Test
    void testReceiptCount() {

        Receipt receipt = receiptService.getReceipt(mockProducts);

        assertNotNull(receipt.getProducts(), "products should not be null");
        assertTrue(receipt.getProducts().containsAll(mockProducts), "all products should be in final receipt");
    }
}