package com.labsky.timotej.util;

import java.math.BigDecimal;

/**
 * @author : Timotej Lábský
 **/
public class TaxRateProvider {

    private static final BigDecimal TAX_RATE = BigDecimal.valueOf(0.12);

    /**
     * @return new instance - safer might someone multiply
     */
    public static BigDecimal getTaxRate() {
        return TAX_RATE;
    }

    private TaxRateProvider() {
    }
}
