package com.labsky.timotej;

import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.Insurance;
import com.labsky.timotej.model.products.promotions.Discount;
import com.labsky.timotej.service.ReceiptService;
import com.labsky.timotej.service.impl.ReceiptServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author : Timotej Lábský
 **/
public class ReceiptServiceTest {

    private static ReceiptService receiptService;

    private static BigDecimal price;
    private static Basket basket;

    @BeforeAll
    public static void init() {
        receiptService = new ReceiptServiceImpl(null, null);
    }

    @BeforeEach
    public void beforeEachInit() {
        basket = new Basket();
        price = valueOf(10L);
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
