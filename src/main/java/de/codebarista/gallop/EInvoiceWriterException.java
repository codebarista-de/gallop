package de.codebarista.gallop;

/**
 * Exception class for all errors that occur when writing an e-invoice.
 */
public class EInvoiceWriterException extends RuntimeException {
    /**
     * Constructs a new {@code EInvoiceWriterException} with the specified message and cause.
     *
     * @param message the detail message
     * @param cause   the exception that caused the e-invoice creation to fail
     */
    public EInvoiceWriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
