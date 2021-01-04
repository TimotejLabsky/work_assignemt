package com.labsky.timotej;

import com.labsky.timotej.exceptions.BasketValidationException;
import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.Earphones;
import com.labsky.timotej.model.products.GenericProduct;
import com.labsky.timotej.model.products.Insurance;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.model.products.SimCard;
import com.labsky.timotej.model.products.promotions.BuyOneGetOneForFree;
import com.labsky.timotej.model.products.promotions.InsuranceDiscount;
import com.labsky.timotej.service.ReceiptService;
import com.labsky.timotej.service.impl.ReceiptServiceImpl;
import com.labsky.timotej.util.validation.definition.BasketValidation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.math.BigDecimal;

import static com.labsky.timotej.util.TaxRateProvider.getTaxRate;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author timotej
 */
class AssignmentSubjectsTest {

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
                .name("BOGOF test")
                .price(productPrice)
                .build();
        this.basket = assertDoesNotThrow((ThrowingSupplier<Basket>) Basket::new);
    }


    // TAXES
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

    // BOGOF
    @Test
    void testSimBOGOF() {

        productWithoutTax.addSalePromotion(new BuyOneGetOneForFree());
        basket.add(productWithoutTax);

        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertEquals(2, receipt.products().get(productWithoutTax),
                "receipt should have 2 SIM cards because of BOGOFF");

        assertEquals(0, receipt.getTotal().compareTo(productPrice),
                "total should be same same as cost of one SIM card " + receipt.getTotal() + " != " + productPrice);
    }

    @Test
    void testSimBOGOFMultiple() {
        final double ONE_PRODUCT_PRICE = 10d;
        final int COUNT_OF_PRODUCTS_IN = 3;

        productWithoutTax.addSalePromotion(new BuyOneGetOneForFree());
        basket.add(productWithoutTax, COUNT_OF_PRODUCTS_IN);

        var receipt = assertDoesNotThrow(() -> receiptService.getReceipt(basket));

        assertEquals(COUNT_OF_PRODUCTS_IN * 2, receipt.products().get(productWithoutTax),
                "receipt should have 2 SIM cards because of BOGOFF");
        assertEquals(0, receipt.getTotal().compareTo(valueOf(ONE_PRODUCT_PRICE * COUNT_OF_PRODUCTS_IN)),
                "total should be same same as cost of one SIM card " + receipt.getTotal() + " != " + (ONE_PRODUCT_PRICE * COUNT_OF_PRODUCTS_IN));
    }


    // Insurance discount
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

        var expectedValue = productPrice.multiply(valueOf(.8d));
        assertEquals(0, insurance.getPrice().compareTo(expectedValue),
                "total should be half of products price " + insurance.getPrice() + " != " + expectedValue);
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

        var expectedValue = productPrice.multiply(BigDecimal.valueOf(COUNT));
        assertEquals(0, receipt.getTotal().compareTo(expectedValue),
                "total should be half of products price " + receipt.getTotal() + " != " + expectedValue);
    }

    // Sim count restriction
    @Test
    void testMaxNumberOfSimsInOnePurchase() {
        var basket = assertDoesNotThrow((ThrowingSupplier<Basket>) Basket::new);

        var product = SimCard.builder()
                .name("BOGOF test")
                .price(productPrice)
                .build();
        basket.add(product, 9);

        assertDoesNotThrow(() -> receiptService.getReceipt(basket),
                "should NOT throw error because number of number of products is less than it should be");

        basket.getProducts().computeIfPresent(product, (p, c) -> c = 11);
        assertThrows(BasketValidationException.class, () -> receiptService.getReceipt(basket),
                "should throw error because number of number of products is higher than it should be");
    }
}
