package com.labsky.timotej.model;

import com.labsky.timotej.model.products.Product;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author timotej
 */
public record Receipt(List<Product> products,
                      String casRegisterUuid,
                      LocalDateTime time,
                      double total,
                      String uuid,
                      String contractorUuid) {
}
