package com.labsky.timotej.util.validation.definition;

import com.labsky.timotej.model.Basket;
import com.labsky.timotej.model.products.SimCard;

import java.util.Map;
import java.util.function.Predicate;

/**
 * Definition of validation Predicates
 * define predicate there onece and chain it with and or operands as they will be needed
 *
 * @author : Timotej Lábský
 **/
public interface ValidationTrigger extends Predicate<Basket> {

    /**
     * validates if count of all instances of SimCard class si lower than 10
     */
    ValidationTrigger simValidation = basket -> basket.getProducts().entrySet().stream()
            .filter(entry -> entry.getKey() instanceof SimCard)
            .mapToInt(Map.Entry::getValue)
            .sum() > 10;

    // define other Laws(predicates) here
}
