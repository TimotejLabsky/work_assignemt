package com.labsky.timotej.model;

import com.labsky.timotej.model.products.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author timotej
 */
public class Receipt {
    private List<Product> products;
    private UUID cashRegisterUuid;
    private LocalDateTime time;
    private Double total;
    private UUID uuid;
    private UUID contractorUui;


    public Receipt() {
    }

    public Receipt(List<Product> products, UUID cashRegisterUuid, LocalDateTime time, Double total, UUID uuid, UUID contractorUui) {
        this.products = products;
        this.cashRegisterUuid = cashRegisterUuid;
        this.time = time;
        this.total = total;
        this.uuid = uuid;
        this.contractorUui = contractorUui;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Double getTotal() {
        return products.stream()
                .mapToDouble(Product::getPrice)
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
