package com.labsky.timotej;

import com.labsky.timotej.exceptions.SimCardCountRestrictionException;
import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.*;
import com.labsky.timotej.model.products.constraints.HasTax;
import com.labsky.timotej.model.products.promotions.BuyOneGetOneForFree;
import com.labsky.timotej.model.products.promotions.Discount;
import com.labsky.timotej.model.products.promotions.InsuranceDiscount;
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

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import static com.labsky.timotej.model.products.constraints.HasTax.TAX_RATE;
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

    private static ReceiptService receiptService;

    private Product productWithoutTax;
    private Basket basket;

    @BeforeAll
    public static void init() {
        receiptService = new ReceiptServiceImpl(null, null);
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
        final double PRODUCT_PRICE = 10d;
        final double PRODUCT_TAX_PRICE_INCREASE = TAX_RATE.multiply(BigDecimal.valueOf(PRODUCT_PRICE)).doubleValue();

        var product = GenericProduct.builder()
                .name("Generic product has TAX")
                .price(PRODUCT_PRICE)
                .build();

        assertNotNull(product, "product should not be empty");
        assertEquals(PRODUCT_TAX_PRICE_INCREASE, ((GenericProduct) product).getTax(), "tax should be on ");
    }

    @Test
    void testSimBOGOFSimple() {

        productWithoutTax.addSalePromotion(new BuyOneGetOneForFree());
        basket.add(productWithoutTax);

        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertEquals(2, receipt.products().get(productWithoutTax), "receipt should have 2 SIM cards because of BOGOFF");
        assertEquals(ONE_PRODUCT_PRICE, receipt.getTotal(), "total should be same same as cost of one SIM card");
    }

    @Test
    void testSimBOGOFMultiple() {
        final double ONE_PRODUCT_PRICE = 10d;
        final int COUNT_OF_PRODUCTS_IN = 3;

        productWithoutTax.addSalePromotion(new BuyOneGetOneForFree());
        basket.add(productWithoutTax, COUNT_OF_PRODUCTS_IN);

        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertEquals(COUNT_OF_PRODUCTS_IN * 2, receipt.products().get(productWithoutTax), "receipt should have 2 SIM cards because of BOGOFF");
        assertEquals(ONE_PRODUCT_PRICE * COUNT_OF_PRODUCTS_IN, receipt.getTotal(), "total should be same same as cost of one SIM card");
    }


    @Test
    void testInsuranceDiscountWhenEarphonesPurchased() {
        final double ONE_PRODUCT_PRICE = 10d;

        var earphones = Earphones.builder()
                .name("Earphones")
                .price(ONE_PRODUCT_PRICE)
                .build();
        var insurance = Insurance.builder()
                .name("Insurance")
                .price(ONE_PRODUCT_PRICE)
                .salePromotions(new InsuranceDiscount())
                .build();

        basket.add(earphones);
        basket.add(insurance);

        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertEquals(ONE_PRODUCT_PRICE * .8d, insurance.getPrice(), "total should be half of products price");
    }

    @Test
    void testInsuranceNotDiscountedWhenEarphonesNotPurchased() {
        final int COUNT = 4;

        var insurance = Insurance.builder()
                .name("Insurance")
                .price(ONE_PRODUCT_PRICE)
                .build();

        basket.add(insurance, COUNT);

        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertEquals(COUNT * ONE_PRODUCT_PRICE, receipt.getTotal(), "total should be half of products price");
    }

    @Test
    void testMaxNumberOfSimsInOnePurchase() {
        var basket = assertDoesNotThrow((ThrowingSupplier<Basket>) Basket::new);

        var product = SimCard.builder()
                .name("BOGOFF test")
                .price(ONE_PRODUCT_PRICE)
                .build();
        basket.add(product, 9);

        assertDoesNotThrow(() -> receiptService.getReceipt(basket),
                "should NOT throw error because number of number of products is less than it should be");

        basket.getProducts().computeIfPresent(product, (p, c) -> c = 11);
        assertThrows(SimCardCountRestrictionException.class, () -> receiptService.getReceipt(basket),
                "should throw error because number of number of products is higher than it should be");
    }
}
