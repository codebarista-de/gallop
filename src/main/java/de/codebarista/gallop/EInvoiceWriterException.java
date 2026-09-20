package de.codebarista.gallop;

/**
 * Exception class for all errors that occur when writing an e-invoice.
 */
public class EInvoiceWriterException extends RuntimeException {
    public EInvoiceWriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
