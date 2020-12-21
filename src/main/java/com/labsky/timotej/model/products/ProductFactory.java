package com.labsky.timotej.model.products;

import com.labsky.timotej.model.constants.ProductTypes;
import com.labsky.timotej.model.constants.ProductsFileFields;
import com.labsky.timotej.model.products.promotions.Discount;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * @author timotej
 */
public class ProductFactory {

    public static Product getProduct(final String productString) {
        String[] productFields = productString.split(ProductsFileFields.FIELD_SEPARATOR);

        return switch (productFields[ProductsFileFields.TYPE]) {
            case ProductTypes.GENERIC_PRODUCT -> GenericProduct.builder()
                    .name(productFields[ProductsFileFields.NAME])
                    .price(parseDouble(productFields[ProductsFileFields.PRICE]))
                    .currency(productFields[ProductsFileFields.CURRENCY])
                    .build();
            case ProductTypes.SIM -> SimCard.builder()
                    .name(productFields[ProductsFileFields.NAME])
                    .price(parseDouble(productFields[ProductsFileFields.PRICE]))
                    .currency(productFields[ProductsFileFields.CURRENCY])
                    .build();
            case ProductTypes.INSURANCE -> Insurance.builder()
                    .name(productFields[ProductsFileFields.NAME])
                    .price(parseDouble(productFields[ProductsFileFields.PRICE]))
                    .currency(productFields[ProductsFileFields.CURRENCY])
                    .salePromotions(new Discount(Double.valueOf(20.0d))) // TODO not hardcode it like this
                    .build();
            case ProductTypes.EARPHONES -> Earphones.builder()
                    .name(productFields[ProductsFileFields.NAME])
                    .price(parseDouble(productFields[ProductsFileFields.PRICE]))
                    .currency(productFields[ProductsFileFields.CURRENCY])
                    .build();
            default -> null;
        };
    }
}
