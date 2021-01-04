package com.labsky.timotej.util.validation.definition;

import com.labsky.timotej.model.Basket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Collections.emptySet;

/**
 * functional interface to define operation over Basket validation
 * <p>
 *     and, or - functions will chain multiple Laws for validation
 * </p>
 * @author timotej
 */
public interface BasketValidation extends Function<Basket, Set<Law>> {
    BasketValidation empty = $ -> emptySet();

    BasketValidation simValidation = law(ValidationTrigger.simValidation, Law.MAX_NUMBER_OF_SIM_CARDS);

    static BasketValidation law(final Predicate<Basket> predicate, final Law law) {
        return basket -> predicate.test(basket) ? Collections.singleton(law) : emptySet();
    }

    default BasketValidation and(final BasketValidation other) {
        return basket -> {
            final Set<Law> left = this.apply(basket);
            final Set<Law> right = other.apply(basket);

            final Set<Law> merged = new HashSet<>(left);
            merged.addAll(right);

            return merged;
        };
    }

    default BasketValidation or(BasketValidation other) {
        return basket -> {
            final Set<Law> firstResult = this.apply(basket);
            return !firstResult.isEmpty() ? firstResult : other.apply(basket);
        };
    }
}
