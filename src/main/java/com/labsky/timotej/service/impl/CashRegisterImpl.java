package com.labsky.timotej.service.impl;

import com.labsky.timotej.exceptions.ConstrainValidationException;
import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.Receipt;
import com.labsky.timotej.service.CashRegister;
import com.labsky.timotej.service.ReceiptService;
import com.labsky.timotej.util.Printer;

import java.util.UUID;

import static java.lang.System.err;
import static java.lang.System.out;

/**
 * @author timotej
 */
public class CashRegisterImpl implements CashRegister {

    private final ReceiptService receiptService;
    public static UUID contractorUuid = UUID.randomUUID();


    public CashRegisterImpl() {
        this.receiptService = new ReceiptServiceImpl(UUID.randomUUID(), contractorUuid);
    }

    @Override
    public void checkout(Basket basket) {
        Receipt receipt = null;
        try {
            receipt = receiptService.getReceipt(basket);
        } catch (ConstrainValidationException e) {
            err.printf("Basket Validation error %s%n", e.getMessage());
            return;
        }

        out.println("Have a nice day, here is your receipt");
        Printer.print(receipt);
    }
}
