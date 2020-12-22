package com.labsky.timotej.service;

import com.labsky.timotej.model.Basket;

/**
 * @author timotej
 */
public interface CashRegister {
    void checkout(Basket basket);
}
