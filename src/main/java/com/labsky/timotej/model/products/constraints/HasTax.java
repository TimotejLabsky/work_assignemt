package com.labsky.timotej.model.products.constraints;

/**
 * @author timotej
 */
public interface HasTax {
    float TAX_RATE = 1.12f;

    double getTax();
}
