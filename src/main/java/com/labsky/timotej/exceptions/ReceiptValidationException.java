package com.labsky.timotej.exceptions;

/**
 * @author timotej
 */
public abstract class ReceiptValidationException extends Exception{

    public ReceiptValidationException(String message) {
        super(message);
    }
}
