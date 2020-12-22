package com.labsky.timotej.model.products.constraints;

import com.labsky.timotej.exceptions.ConstrainValidationException;
import com.labsky.timotej.model.Basket;

/**
 * @author timotej
 */
public interface Constrain {
    boolean isValid(Basket basket) throws ConstrainValidationException;
}