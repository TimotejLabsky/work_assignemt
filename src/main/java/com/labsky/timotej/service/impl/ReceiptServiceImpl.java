package com.labsky.timotej.service.impl;

import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.constraints.HasTax;
import com.labsky.timotej.model.Receipt;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.model.products.promotions.SalePromotion;
import com.labsky.timotej.service.ReceiptService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

/**
 * @author timotej
 */
public class ReceiptServiceImpl implements ReceiptService {
    @Override
    public Receipt getReceipt(Basket basket) {

        List<Product> products = basket.getProducts().stream()
                .map(calculateTax())
                .collect(toList());


        return new Receipt(products,
                null, // TODO constant
                LocalDateTime.now(),
                getTotal(products),
                randomUUID(),
                null); // TODO constant
    }

    private Double getTotal(List<Product> products) {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    private Function<Product, Product> calculateDiscount() {
        return product -> {
            if (product instanceof SalePromotion) {
                product.setPrice(product.getPrice() + ((HasTax) product).getTax());
            }
            return product;
        };
    }

    /**
     * change price of product on receipt to be with TAX included
     *
     * @return
     */
    private Function<Product, Product> calculateTax() {
        return product -> {
            if (product instanceof HasTax) {
                product.setPrice(product.getPrice() + ((HasTax) product).getTax());
            }
            return product;
        };
    }
}
