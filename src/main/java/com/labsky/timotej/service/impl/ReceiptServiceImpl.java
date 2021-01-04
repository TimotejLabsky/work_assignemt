package com.labsky.timotej.service.impl;

import com.labsky.timotej.exceptions.BasketValidationException;
import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.Receipt;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.service.ReceiptService;
import com.labsky.timotej.util.validation.BasketValidationProvider;
import com.labsky.timotej.util.validation.definition.BasketValidation;
import com.labsky.timotej.util.validation.definition.Law;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static java.util.UUID.randomUUID;

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
    public Receipt getReceipt(Basket basket) throws BasketValidationException {

        applySale(basket);
        applyTax(basket);
        applyValidation(basket, BasketValidationProvider.getValidator());

        return new Receipt(basket.getProducts(),
                this.cashRegisterUuid,
                LocalDateTime.now(),
                randomUUID(),
                contractorUuid);
    }

    private static void applyValidation(final Basket basket, final BasketValidation validation) throws BasketValidationException {
        Set<Law> violation = validation.apply(basket);

        if (!violation.isEmpty()) {
            throw new BasketValidationException(violation.toString());
        }
    }

    private static void applyTax(Basket basket) {
        basket.getProducts().keySet()
                .forEach(p -> p.setPrice(p.getTax().add(p.getPrice())));
    }

    /**
     * current implementation will chain all sales = if product discounted by 20% and has another 30% dicount
     * it will discount it by 20% and the discounted price will be discounted by another 30%
     *
     * @param basket
     */
    private static void applySale(Basket basket) {
        for (Product product : basket.getProducts().keySet()) {
            for (int j = 0; j < product.getSalePromotions().size(); j++) {
                product.getSalePromotions().get(j).apply(product, basket);
            }
        }
    }


}
