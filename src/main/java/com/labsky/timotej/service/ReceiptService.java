package com.labsky.timotej.service;

import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.Receipt;

/**
 * @author timotej
 */
public interface ReceiptService {
    Receipt getReceipt(Basket basket);
}
