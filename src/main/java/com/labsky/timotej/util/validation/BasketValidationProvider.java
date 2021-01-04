package com.labsky.timotej.util.validation;

import com.labsky.timotej.util.validation.definition.BasketValidation;

import static com.labsky.timotej.util.validation.definition.BasketValidation.simValidation;

/**
 * Provides validator for Basket - chaining of validations for
 * @author : Timotej Lábský
 **/
public class BasketValidationProvider {

    /**
     * definition of validator and chaining of Laws in validation
     * @return
     */
    public static BasketValidation getValidator() {
        return BasketValidation.empty
                .and(simValidation);
    }

    private BasketValidationProvider() {
    }
}
