package com.labsky.timotej.service;

import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.Receipt;
import com.labsky.timotej.model.products.GenericProduct;
import com.labsky.timotej.model.products.Insurance;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.model.products.promotions.BuyOneGetOneForFree;
import com.labsky.timotej.model.products.promotions.Discount;
import com.labsky.timotej.service.impl.ReceiptServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.math.BigDecimal.valueOf;
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
    private static final BigDecimal price = valueOf(10L);
    private static final BigDecimal mockProductsTotalPrice = valueOf(336L);
    private Basket basket;

    @BeforeAll
    public static void init() {
        receiptService = new ReceiptServiceImpl(null, null);
    }

    @BeforeEach
    public void initMock() {
        mockProducts = new ArrayList<>();
        mockProducts.add(new GenericProduct("SIM card", valueOf(100L), "CHF", emptyList()));
        mockProducts.add(new GenericProduct("phone case", valueOf(100L), "CHF", emptyList()));
        mockProducts.add(new GenericProduct("SIM card", valueOf(100L), "CHF", emptyList()));
        basket = new Basket();
        basket.addAll(mockProducts);
    }


    @Test
    void testReceiptTaxCalculation() {
        Receipt receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertEquals(0, receipt.getTotal().compareTo(mockProductsTotalPrice),
                "total amount should be 336 - 3x112");
    }

    @Test
    void testReceiptTaxCalculationWithInsurance() {
        basket.add(new Insurance("phone insurance", valueOf(100L), "CHF", emptyList()));

        Receipt receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        var expectedValue = mockProductsTotalPrice.add(valueOf(100d));
        assertEquals(0, receipt.getTotal().compareTo(expectedValue),
                "total amount should be mockProductsTotalPrice + 100 - insurance does not have tax");
    }


    @Test
    void testReceiptCount() {

        Receipt receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertNotNull(receipt.products(), "products should not be null");

        assertEquals(mockProducts.size(), receipt.products().size(),
                "number of products in receipt should be same as mockProducts");

        receipt.products().keySet()
                .forEach(p -> assertTrue(mockProducts.contains(p),
                        "all products should be in basket"));
    }

    @Test
    void testSimpleDiscount() {
        final double DISCOUNT_RATE = 20d;

        var product = Insurance.builder()
                .name("Product without taxes")
                .price(price)
                .salePromotions(new Discount(DISCOUNT_RATE))
                .build();

        basket.add(product);


        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket), "This should not throw anything");

        assertEquals(price.multiply(BigDecimal.valueOf(1 - DISCOUNT_RATE / 100)), product.getPrice(), "Product should be discounted by 20%");

    }

    @Test
    void testMultipleDiscounts() {
        final double DISCOUNT_RATE = 20d;

        var product = Insurance.builder()
                .name("Product without taxes")
                .price(price)
                .salePromotions(new Discount(DISCOUNT_RATE))
                .salePromotions(new Discount(DISCOUNT_RATE))
                .build();

        basket.add(product);


        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket), "This should not throw anything");

        assertEquals(price.multiply(valueOf(1 - DISCOUNT_RATE / 100))
                        .multiply(valueOf(1 - DISCOUNT_RATE / 100))
                , product.getPrice(), "Product should be discounted by 20% and again by 20%");

    }


}