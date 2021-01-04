package com.labsky.timotej.model;

import com.labsky.timotej.model.products.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author timotej
 */
public record Receipt(
        Map<Product, Integer> products,
        UUID cashRegisterUuid,
        LocalDateTime time,
        UUID uuid,
        UUID contractorUui) {


    public BigDecimal getTotal() {
        return products.entrySet().stream()
                .map(multiplyPriceByCount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Function<Map.Entry<Product, Integer>, BigDecimal> multiplyPriceByCount() {
        return e -> e.getKey().getPrice().multiply(BigDecimal.valueOf(e.getValue()));
    }


}
