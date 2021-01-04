package com.labsky.timotej.util;

import java.math.BigDecimal;

/**
 * @author : Timotej Lábský
 **/
public class TaxRateProvider {

    /**
     * @return new instance - safer might someone multiply
     */
    public static BigDecimal getTaxRate() {
        return BigDecimal.valueOf(0.12);
    }

    private TaxRateProvider() {
    }
}
