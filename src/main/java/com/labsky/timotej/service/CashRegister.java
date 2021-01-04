package com.labsky.timotej.service;

import com.labsky.timotej.model.Basket;

/**
 * @author timotej
 */
public interface CashRegister {
    /**
     * from basket it implements all necessary steps to finish the order
     * @param basket
     */
    void checkout(Basket basket);
}
