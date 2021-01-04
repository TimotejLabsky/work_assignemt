package com.labsky.timotej.service.impl;

import com.labsky.timotej.exceptions.BasketValidationException;
import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.Receipt;
import com.labsky.timotej.service.CashRegister;
import com.labsky.timotej.service.ReceiptService;
import com.labsky.timotej.util.Printer;

import java.util.UUID;

import static java.lang.System.err;

/**
 * @author timotej
 */
public class CashRegisterImpl implements CashRegister {

    private final ReceiptService receiptService;
    public static final UUID contractorUuid = UUID.randomUUID();


    public CashRegisterImpl() {
        this.receiptService = new ReceiptServiceImpl(UUID.randomUUID(), contractorUuid);
    }

    @Override
    public void checkout(Basket basket) {
        Receipt receipt;

        try {
            receipt = receiptService.getReceipt(basket);
        } catch (BasketValidationException e) {
            err.printf("Basket Validation error %s%n", e.getMessage());
            return;
        }

        Printer.print(receipt);
    }
}
