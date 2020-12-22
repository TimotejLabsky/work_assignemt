package com.labsky.timotej.service.impl;

import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.Receipt;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.model.products.constraints.HasTax;
import com.labsky.timotej.service.ReceiptService;
import com.labsky.timotej.util.ProductCountPair;

import java.time.LocalDateTime;

import static java.util.UUID.randomUUID;

/**
 * @author timotej
 */
public class ReceiptServiceImpl implements ReceiptService {
    @Override
    public Receipt getReceipt(Basket basket) {

        applySale(basket);
        applyTax(basket);

        return new Receipt(basket.getProducts(),
                null, // TODO constant
                LocalDateTime.now(),
                getTotal(basket),
                randomUUID(),
                null); // TODO constant
    }

    private void applyTax(Basket basket) {
        basket.getProducts().stream()
                .map(ProductCountPair::product)
                .filter(HasTax.class::isInstance)
                .forEach(p -> p.setPrice(((HasTax) p).getTax() + p.getPrice()));
    }

    private void applySale(Basket basket) {
        for (int i = 0; i < basket.getProducts().size(); i++) {
            ProductCountPair productCountPair = basket.getProducts().get(i);
            for (int j = 0; j < productCountPair.product().getSalePromotions().size(); j++) {
                productCountPair.product().getSalePromotions().get(j).apply(productCountPair);
            }
        }
    }

    private static Double getTotal(Basket basket) {
        return basket.getProducts().stream()
                .map(ProductCountPair::product)
                .mapToDouble(Product::getPrice)
                .sum();
    }


}
