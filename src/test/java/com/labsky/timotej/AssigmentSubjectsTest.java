package com.labsky.timotej;

import com.labsky.timotej.exceptions.ProductNotFoundException;
import com.labsky.timotej.exceptions.SimCardCountRestrictionException;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author timotej
 */
class AssigmentSubjectsTest {

    public final double ONE_PRODUCT_PRICE = 10d;

    private static ProductService productService;
    private static ReceiptService receiptService;
    private static final ProductRepository productRepository = ProductRepositoryImpl.getInstance();

    private Product productWithoutTax;
    private Basket basket;

    @BeforeAll
    public static void init() {
        productService = new ProductServiceImpl(productRepository);
        receiptService = new ReceiptServiceImpl();
    }

    @BeforeEach
    public void initProductWithoutTax() {
        this.productWithoutTax = Insurance.builder()
                .name("BOGOFF test")
                .price(ONE_PRODUCT_PRICE)
                .build();
        this.basket = assertDoesNotThrow((ThrowingSupplier<Basket>) Basket::new);
    }


    @Test
    void testTax() {
        Product product = assertDoesNotThrow(() -> productService.findByName("SIM card"));

        assertNotNull(product, "product should not be empty");
        assertTrue(product instanceof SimCard, "String \"Sim card\" should represent insurance");
        assertEquals(2.4d, ((SimCard) product).getTax(), "tax should be on ");
    }

    @Test
    void testSimBOGOFSimple() {

        productWithoutTax.addSalePromotion(new BuyOneGetOneForFree());
        basket.add(productWithoutTax);

        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertEquals(2, receipt.products().get(0).count(), "receipt should have 2 SIM cards because of BOGOFF");
        assertEquals(ONE_PRODUCT_PRICE, receipt.getTotal(), "total should be same same as cost of one SIM card");
    }

    @Test
    void testSimBOGOFMultiple() {
        final double ONE_PRODUCT_PRICE = 10d;
        final int COUNT_OF_PRODUCTS_IN = 3;

        productWithoutTax.addSalePromotion(new BuyOneGetOneForFree());
        basket.add(productWithoutTax, COUNT_OF_PRODUCTS_IN);

        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertEquals(COUNT_OF_PRODUCTS_IN * 2, receipt.products().get(0).count(), "receipt should have 2 SIM cards because of BOGOFF");
        assertEquals(ONE_PRODUCT_PRICE * COUNT_OF_PRODUCTS_IN, receipt.getTotal(), "total should be same same as cost of one SIM card");
    }

    @Test
    void testInsuranceDiscount() {
        final double ONE_PRODUCT_PRICE = 10d;
        // half of price
        final double DISCOUNT_PERCENTAGE = 50d;


        productWithoutTax.addSalePromotion(new Discount(DISCOUNT_PERCENTAGE));
        basket.add(productWithoutTax);

        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertEquals(ONE_PRODUCT_PRICE / 2, receipt.getTotal(), "total should be half of products price");
    }

    @Test
    void testMaxNumberOfSimsInOnePurchase() {
        final double ONE_PRODUCT_PRICE = 10d;

        var basket = assertDoesNotThrow((ThrowingSupplier<Basket>) Basket::new);

        var product = SimCard.builder()
                .name("BOGOFF test")
                .price(ONE_PRODUCT_PRICE)
                .build();
        basket.add(product, 11);

        var receipt = assertThrows(SimCardCountRestrictionException.class, () -> receiptService.getReceipt(basket),
                "should throw error because number of number of products is higher than it should be");
    }
}
