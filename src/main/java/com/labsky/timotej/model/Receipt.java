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
    private UUID casRegisterUuid;
    private LocalDateTime time;
    private double total;
    private UUID uuid;
    private UUID contractorUui;


    public Receipt() {
    }

    public Receipt(List<Product> products, UUID casRegisterUuid, LocalDateTime time, double total, UUID uuid, UUID contractorUui) {
        this.products = products;
        this.casRegisterUuid = casRegisterUuid;
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

    // TODO nice formated print
    @Override
    public String toString() {
        return "Receipt{" +
                "products=" + products +
                ", casRegisterUuid=" + casRegisterUuid +
                ", time=" + time +
                ", total=" + total +
                ", uuid=" + uuid +
                ", contractorUui=" + contractorUui +
                '}';
    }
}
