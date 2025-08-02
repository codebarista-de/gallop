package de.codebarista.gallop.xrechnung.model;


/**
 * Invoice Type Code (BT-3)<br>
 * Code indicating the functional type of the invoice, according to UNTDID 1001.
 * <p>
 * The values in this class are an exhaustive enumeration according to the specification.
 */
public enum InvoiceType {
    /**
     * Commercial invoice.
     */
    COMMERCIAL_INVOICE("380"),

    /**
     * (Invoice) Correction (e.g. "Stornorechnung").<br>
     * Provide a reference to the original preceding invoice or credit note, see “preceding invoice reference” (BG-3).
     * <br><br>
     * In the invoice correction, the signs of the quantity information (BT-129 Invoiced quantity)
     * and the amounts resulting from calculations with negative quantities are reversed, while the prices
     * specified in BT-146 (Item net price) and BT-148 (Item gross price) must not be negative
     * according to the underlying EN 16931-1 standard (see BR-27 and BR-28 / Chapter 12.2 of the specification).
     * This results, for example, in a cancellation invoice with a negative total amount for an original invoice
     * with a positive total amount.<br>
     * <a href="https://github.com/itplr-kosit/validator-configuration-xrechnung/issues/58">See discussion</a>
     */
    CORRECTED_INVOICE("384"),

    /**
     * Voucher/Credit Note (Gutschein/Gutschrift).<br>
     * Treated as a commercial credit note. No reference to a preceding invoice is required.
     */
    CREDIT_NOTE("381"),

    /**
     * Credit Note under VAT Act (Gutschrift nach UStG)<br>
     * Treated as a credit note in accordance with § 14 (2) sentence 2 of the German VAT Act (UStG).
     */
    SELF_BILLED_INVOICE("389"),

    /**
     * Partial invoice.
     */
    PARTIAL_INVOICE("326"),

    /**
     * Partial construction invoice.
     */
    PARTIAL_CONSTRUCTION_INVOICE("875"),

    /**
     * Partial final construction invoice.
     */
    PARTIAL_FINAL_CONSTRUCTION_INVOICE("876"),

    /**
     * Final construction invoice.
     */
    FINAL_CONSTRUCTION_INVOICE("877"),

    /**
     * Unofficial custom invalid type, validation will fail.
     */
    UNSUPPORTED("000");

    private final String value;

    InvoiceType(String value) {
        this.value = value;
    }

    /**
     * Gets the corresponding value
     */
    public String getValue() {
        return value;
    }
}
