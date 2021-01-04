package com.labsky.timotej.service;

import com.labsky.timotej.model.Basket;

/**
 * Final stage for basket
 *
 * @author timotej
 */
public interface CashRegister {
    /**
     * implements all necessary steps to finish Basket the order might implement confirmation
     * or overview or what ever before receipt is printed
     *
     * @param basket
     */
    void checkout(Basket basket);
}
