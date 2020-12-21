package com.labsky.timotej.model;

import com.labsky.timotej.model.constants.ProductTypes;
import com.labsky.timotej.model.constants.ProductsFileFields;
import com.labsky.timotej.model.products.Earphones;
import com.labsky.timotej.model.products.GenericProduct;
import com.labsky.timotej.model.products.Insurance;
import com.labsky.timotej.model.products.Product;
import com.labsky.timotej.model.products.SimCard;


import static java.lang.Integer.parseInt;

/**
 * @author timotej
 */
public class ProductFactory {

    public static Product getProduct(final String productString) {
        String[] productFields = productString.split(ProductsFileFields.FIELD_SEPARATOR);

        switch (productFields[ProductsFileFields.TYPE]) {
            case ProductTypes.GENERIC_PRODUCT:
                return GenericProduct.builder()
                        .name(productFields[ProductsFileFields.NAME])
                        .price(parseInt(productFields[ProductsFileFields.PRICE]))
                        .currency(productFields[ProductsFileFields.CURRENCY])
                        .build();
            case ProductTypes.SIM:
                return SimCard.builder()
                        .name(productFields[ProductsFileFields.NAME])
                        .price(parseInt(productFields[ProductsFileFields.PRICE]))
                        .currency(productFields[ProductsFileFields.CURRENCY])
                        .build();
            case ProductTypes.INSURANCE:
                return Insurance.builder()
                        .name(productFields[ProductsFileFields.NAME])
                        .price(parseInt(productFields[ProductsFileFields.PRICE]))
                        .currency(productFields[ProductsFileFields.CURRENCY])
                        .build();
            case ProductTypes.EARPHONES:
                return Earphones.builder()
                        .name(productFields[ProductsFileFields.NAME])
                        .price(parseInt(productFields[ProductsFileFields.PRICE]))
                        .currency(productFields[ProductsFileFields.CURRENCY])
                        .build();
        }

        //TODO solve null
        return null;
    }
}
