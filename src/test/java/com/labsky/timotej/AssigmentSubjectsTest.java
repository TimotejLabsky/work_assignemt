package com.labsky.timotej;

import com.labsky.timotej.exceptions.SimCardCountRestrictionException;
import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.*;
import com.labsky.timotej.model.products.promotions.BuyOneGetOneForFree;
import com.labsky.timotej.model.products.promotions.InsuranceDiscount;
import com.labsky.timotej.service.ReceiptService;
import com.labsky.timotej.service.impl.ReceiptServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.math.BigDecimal;

import static com.labsky.timotej.util.TaxRateProvider.getTaxRate;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author timotej
 */
class AssigmentSubjectsTest {

    private static BigDecimal productPrice;
    private static BigDecimal productTaxPriceIncrease;


    private static ReceiptService receiptService;

    private Product productWithoutTax;
    private Basket basket;

    @BeforeAll
    public static void init() {
        receiptService = new ReceiptServiceImpl(null, null);
    }

    @BeforeEach
    public void initProductWithoutTax() {
        productPrice = valueOf(10L);
        productTaxPriceIncrease = productPrice.multiply(getTaxRate());

        this.productWithoutTax = Insurance.builder()
                .name("BOGOFF test")
                .price(productPrice)
                .build();
        this.basket = assertDoesNotThrow((ThrowingSupplier<Basket>) Basket::new);
    }


    @Test
    void testTax() {
        final BigDecimal PRODUCT_TAX_PRICE_INCREASE = productPrice.multiply(getTaxRate());

        var product = GenericProduct.builder()
                .name("Generic product has TAX")
                .price(productPrice)
                .build();

        assertNotNull(product, "product should not be empty");
        assertEquals(PRODUCT_TAX_PRICE_INCREASE, product.getTax(), "tax should be on ");
    }

    /**
     * three products - two with tax, one without
     */
    @Test
    void testTaxMoreProducts() {

        final BigDecimal FINAL_PRICE = productPrice.multiply(valueOf(3))
                .add(productTaxPriceIncrease.multiply(valueOf(2)));


        var productWithTax0 = GenericProduct.builder()
                .name("Generic product has TAX")
                .price(productPrice)
                .build();

        var productWithTax1 = GenericProduct.builder()
                .name("Generic product has TAX")
                .price(productPrice)
                .build();
        var productWithoutTax = Insurance.builder()
                .name("Insurance has no TAX")
                .price(productPrice)
                .build();

        basket.add(productWithTax0);
        basket.add(productWithTax1);
        basket.add(productWithoutTax);

        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));
        assertEquals(FINAL_PRICE, receipt.getTotal());
    }

    @Test
    void testSimBOGOFSimple() {

        productWithoutTax.addSalePromotion(new BuyOneGetOneForFree());
        basket.add(productWithoutTax);

        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertEquals(2, receipt.products().get(productWithoutTax), "receipt should have 2 SIM cards because of BOGOFF");
        assertEquals(productPrice, receipt.getTotal(), "total should be same same as cost of one SIM card");
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


        var earphones = Earphones.builder()
                .name("Earphones")
                .price(productPrice)
                .build();
        var insurance = Insurance.builder()
                .name("Insurance")
                .price(productPrice)
                .salePromotions(new InsuranceDiscount())
                .build();

        basket.add(earphones);
        basket.add(insurance);

        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertEquals(productPrice.multiply(valueOf(.8d)), insurance.getPrice(), "total should be half of products price");
    }

    @Test
    void testInsuranceNotDiscountedWhenEarphonesNotPurchased() {
        final int COUNT = 4;

        var insurance = Insurance.builder()
                .name("Insurance")
                .price(productPrice)
                .build();

        basket.add(insurance, COUNT);

        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertEquals(productPrice.multiply(BigDecimal.valueOf(COUNT)), receipt.getTotal(), "total should be half of products price");
    }

    @Test
    void testMaxNumberOfSimsInOnePurchase() {
        var basket = assertDoesNotThrow((ThrowingSupplier<Basket>) Basket::new);

        var product = SimCard.builder()
                .name("BOGOFF test")
                .price(productPrice)
                .build();
        basket.add(product, 9);

        assertDoesNotThrow(() -> receiptService.getReceipt(basket),
                "should NOT throw error because number of number of products is less than it should be");

        basket.getProducts().computeIfPresent(product, (p, c) -> c = 11);
        assertThrows(SimCardCountRestrictionException.class, () -> receiptService.getReceipt(basket),
                "should throw error because number of number of products is higher than it should be");
    }
}
