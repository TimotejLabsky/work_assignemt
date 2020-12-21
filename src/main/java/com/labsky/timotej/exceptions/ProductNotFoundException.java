package com.labsky.timotej.exceptions;

/**
 * @author timotej
 */
public class ProductNotFoundException extends ReceiptValidationException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
