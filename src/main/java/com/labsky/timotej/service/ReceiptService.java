package com.labsky.timotej.service;

import com.labsky.timotej.exceptions.ConstrainValidationException;
import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.Receipt;

/**
 * @author timotej
 */
public interface ReceiptService {
    /**
     * returns receipt from basket after applying all necessary rules/sales and calculating of prices
     *
     * @param basket contains products and their count requested by user
     * @return receipt with all calculations done
     * @throws ConstrainValidationException when basket validation fails
     */
    Receipt getReceipt(Basket basket) throws ConstrainValidationException;
}
