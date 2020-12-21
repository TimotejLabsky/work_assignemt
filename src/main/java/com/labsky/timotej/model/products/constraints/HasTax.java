package com.labsky.timotej.model.products.constraints;

/**
 * @author timotej
 */
public interface HasTax {
    double TAX_RATE = 0.12d;

    Double getTax();
}
