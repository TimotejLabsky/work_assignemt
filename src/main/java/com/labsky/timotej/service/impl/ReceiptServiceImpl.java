package com.labsky.timotej.service.impl;

import com.labsky.timotej.exceptions.ConstrainValidationException;
import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.Receipt;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.model.products.constraints.Constrain;
import com.labsky.timotej.model.products.constraints.HasTax;
import com.labsky.timotej.service.ReceiptService;
import com.labsky.timotej.util.ProductCountPair;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

/**
 * @author timotej
 */
public class ReceiptServiceImpl implements ReceiptService {
    @Override
    public Receipt getReceipt(Basket basket) throws ConstrainValidationException {

        applySale(basket);
        applyTax(basket);
        applyValidation(basket);

        return new Receipt(basket.getProducts(),
                null, // TODO constant
                LocalDateTime.now(),
                getTotal(basket),
                randomUUID(),
                null); // TODO constant
    }

    private void applyValidation(Basket basket) throws ConstrainValidationException {
        List<Constrain> productToValidate = basket.getProducts().stream()
                .map(ProductCountPair::product)
                .filter(Constrain.class::isInstance)
                .map(Constrain.class::cast)
                .collect(toList());

        for (Constrain constrain : productToValidate) {
            constrain.isValid(basket);
        }
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
