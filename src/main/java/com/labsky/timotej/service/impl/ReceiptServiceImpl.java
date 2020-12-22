package com.labsky.timotej.service.impl;

import com.labsky.timotej.exceptions.ConstrainValidationException;
import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.Receipt;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.model.products.constraints.Constrain;
import com.labsky.timotej.model.products.constraints.HasTax;
import com.labsky.timotej.service.ReceiptService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

/**
 * @author timotej
 */
public class ReceiptServiceImpl implements ReceiptService {
    public final UUID cashRegisterUuid;
    public static UUID contractorUuid;

    public ReceiptServiceImpl(UUID cashRegisterUuid, UUID contractorUuid) {
        this.cashRegisterUuid = cashRegisterUuid;
        ReceiptServiceImpl.contractorUuid = contractorUuid;
    }

    @Override
    public Receipt getReceipt(Basket basket) throws ConstrainValidationException {

        applySale(basket);
        applyTax(basket);
        applyValidation(basket);

        return new Receipt(basket.getProducts(),
                this.cashRegisterUuid,
                LocalDateTime.now(),
                randomUUID(),
                contractorUuid);
    }

    private static void applyValidation(Basket basket) throws ConstrainValidationException {
        List<Constrain> productToValidate = basket.getProducts().keySet().stream()
                .filter(Constrain.class::isInstance)
                .map(Constrain.class::cast)
                .collect(toList());

        for (Constrain constrain : productToValidate) {
            constrain.isValid(basket);
        }
    }

    private static void applyTax(Basket basket) {
        basket.getProducts().keySet().stream()
                .filter(HasTax.class::isInstance)
                .forEach(p -> p.setPrice(((HasTax) p).getTax() + p.getPrice()));
    }

    private static void applySale(Basket basket) {
        for (Product product : basket.getProducts().keySet()) {
            for (int j = 0; j < product.getSalePromotions().size(); j++) {
                product.getSalePromotions().get(j).apply(product, basket);
            }
        }
    }


}
