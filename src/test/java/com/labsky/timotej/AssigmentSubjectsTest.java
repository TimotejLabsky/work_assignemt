package com.labsky.timotej;

import com.labsky.timotej.exceptions.ProductNotFoundException;
import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.Insurance;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.model.products.SimCard;
import com.labsky.timotej.model.products.promotions.BuyOneGetOneForFree;
import com.labsky.timotej.model.products.promotions.Discount;
import com.labsky.timotej.repository.ProductRepository;
import com.labsky.timotej.repository.impl.ProductRepositoryImpl;
import com.labsky.timotej.service.ProductService;
import com.labsky.timotej.service.ReceiptService;
import com.labsky.timotej.service.impl.ProductServiceImpl;
import com.labsky.timotej.service.impl.ReceiptServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
        Product product = assertDoesNotThrow(() -> productService.findByName("SIM card"));

        assertNotNull(product, "product should not be empty");
        assertTrue(product instanceof SimCard, "String \"Sim card\" should represent insurance");
        assertEquals(2.4d, ((SimCard) product).getTax(), "tax should be on ");
    }

    @Test
    void testSimBOGOF() {
        final double ONE_PRODUCT_PRICE = 10d;

        var basket = assertDoesNotThrow((ThrowingSupplier<Basket>) Basket::new);

        var product = Insurance.builder()
                .name("BOGOFF test")
                .price(ONE_PRODUCT_PRICE)
                .salePromotions(new BuyOneGetOneForFree())
                .build();
        basket.add(product);

        var receipt = receiptService.getReceipt(basket);

        assertEquals(2, receipt.products().get(0).count(), "receipt should have 2 SIM cards because of BOGOFF");
        assertEquals(ONE_PRODUCT_PRICE, receipt.getTotal(), "total should be same same as cost of one SIM card");
    }

    @Test
    void testInsuranceDiscount() {
        final double ONE_PRODUCT_PRICE = 10d;

        var basket = assertDoesNotThrow((ThrowingSupplier<Basket>) Basket::new);

        var product = Insurance.builder()
                .name("BOGOFF test")
                .price(ONE_PRODUCT_PRICE)
                .salePromotions(new Discount(50d))
                .build();
        basket.add(product);

        var receipt = receiptService.getReceipt(basket);

        assertEquals(ONE_PRODUCT_PRICE/2, receipt.getTotal(), "total should be half of products price");
    }

    //    @Test
    void testMaxNumberOfSimsInOnePurchase() {
        assertTrue(false, "not implemented");
    }
}
