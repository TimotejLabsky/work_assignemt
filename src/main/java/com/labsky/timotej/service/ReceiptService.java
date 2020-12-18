package com.labsky.timotej.service;

import com.labsky.timotej.model.Receipt;
import com.labsky.timotej.model.products.Product;

import java.util.List;

/**
 * @author timotej
 */
public interface ReceiptService {
    Receipt getReceipt(List<Product> basket);
}
