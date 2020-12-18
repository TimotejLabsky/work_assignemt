package com.labsky.timotej.model;

import com.labsky.timotej.model.products.Product;

import java.util.List;

/**
 * @author timotej
 */
public record Receipt(List<Product> products,
                      double total) {
}
