package com.labsky.timotej.model.products.constraints;

import java.math.BigDecimal;

/**
 * @author timotej
 */
public interface HasTax {
    BigDecimal TAX_RATE = new BigDecimal("0.12");

    Double getTax();
}
