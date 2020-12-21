package com.labsky.timotej;

import com.labsky.timotej.exceptions.ProductNotFoundException;
import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.model.products.SimCard;
import com.labsky.timotej.model.products.promotions.BuyOneGetOneForFree;
import com.labsky.timotej.repository.ProductRepository;
import com.labsky.timotej.repository.impl.ProductRepositoryImpl;
import com.labsky.timotej.service.ProductService;
import com.labsky.timotej.service.ReceiptService;
import com.labsky.timotej.service.impl.ProductServiceImpl;
import com.labsky.timotej.service.impl.ReceiptServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
        var basket = assertDoesNotThrow(() -> new Basket("SIM card"));

        // add buy one get one for free promotion to product
        basket.getProducts().get(0).getSalePromotions().add(new BuyOneGetOneForFree());

        var receipt = receiptService.getReceipt(basket);

        assertEquals(2, receipt.getProducts().size(), "receipt should have 2 SIM cards because of BOGOFF");
        assertEquals(basket.getProducts().get(0).getPrice(), receipt.getTotal(), "total should be same same as cost of one SIM card");
    }

    //    @Test
    void testInsuranceDiscount() {

        assertTrue(false, "not implemented");
    }

    //    @Test
    void testMaxNumberOfSimsInOnePurchase() {
        assertTrue(false, "not implemented");
    }
}
