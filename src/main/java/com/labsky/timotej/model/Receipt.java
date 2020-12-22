package com.labsky.timotej.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author timotej
 */
public record Receipt(
        List<ProductCountPair> products,
        UUID cashRegisterUuid,
        LocalDateTime time,
        Double total,
        UUID uuid,
        UUID contractorUui) {


    public Double getTotal() {
        return products.stream()
                .mapToDouble(p -> p.product().getPrice() * p.count())
                .sum();
    }

    // TODO nice formated print
    @Override
    public String toString() {
        return "Receipt{" +
                "products=" + products +
                ", casRegisterUuid=" + cashRegisterUuid +
                ", time=" + time +
                ", total=" + total +
                ", uuid=" + uuid +
                ", contractorUui=" + contractorUui +
                '}';
    }
}
