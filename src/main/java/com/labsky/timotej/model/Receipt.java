package com.labsky.timotej.model;

import com.labsky.timotej.model.products.Product;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * @author timotej
 */
public record Receipt(
        Map<Product, Integer> products,
        UUID cashRegisterUuid,
        LocalDateTime time,
        UUID uuid,
        UUID contractorUui) {


    public Double getTotal() {
        return products.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
    }


}
