package com.labsky.timotej;

import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.Insurance;
import com.labsky.timotej.model.products.promotions.Discount;
import com.labsky.timotej.service.ReceiptService;
import com.labsky.timotej.service.impl.ReceiptServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author : Timotej Lábský
 **/
public class DiscountTest {

    private static ReceiptService receiptService;

    private Basket basket;

    @BeforeAll
    public static void init() {
        receiptService = new ReceiptServiceImpl(null, null);
    }

    @BeforeEach
    public void basketInit() {
        this.basket = new Basket();
    }


    @Test
    void testSimpleDiscount() {
        final double DISCOUNT_RATE = 20d;
        final double PRICE = 10d;

        var product = Insurance.builder()
                .name("Product without taxes")
                .price(PRICE)
                .salePromotions(new Discount(DISCOUNT_RATE))
                .build();

        basket.add(product);


        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket), "This should not throw anything");

        assertEquals(PRICE * (1 - DISCOUNT_RATE / 100), product.getPrice(), "Product should be discounted by 20%");

    }

    @Test
    void testMultipleDiscounts() {
        final double DISCOUNT_RATE = 20d;
        final double PRICE = 10d;

        var product = Insurance.builder()
                .name("Product without taxes")
                .price(PRICE)
                .salePromotions(new Discount(DISCOUNT_RATE))
                .salePromotions(new Discount(DISCOUNT_RATE))
                .build();

        basket.add(product);


        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket), "This should not throw anything");

        assertEquals(PRICE * (1 - DISCOUNT_RATE / 100) * (1 - DISCOUNT_RATE / 100)
                , product.getPrice(), "Product should be discounted by 20% and again by 20%");

    }


}
