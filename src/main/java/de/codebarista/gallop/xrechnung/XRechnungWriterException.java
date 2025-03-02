package de.codebarista.gallop.xrechnung;

/**
 * Exception class for all errors that occur when writing a XRechnung.
 */
public class XRechnungWriterException extends RuntimeException {
    XRechnungWriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
