package de.codebarista.gallop.xrechnung.model;

import java.time.OffsetDateTime;

/**
 * A group of information elements providing details about one or more preceding invoices (BG-3).<br>
 * This information element should be used when a prior invoice is being corrected,
 * a final invoice refers to previous partial invoices, or a final invoice refers to prior advance payment invoices.
 */
public class PrecedingInvoiceReference {
    /**
     * The identifier of the preceding invoice being referenced (BT-25).<br>
     * If not unique, also provide the preceding invoice issue date (BT-26)
     */
    private String precedingInvoiceReference;

    /**
     * The date on which the preceding invoice was issued (BT-26).<br>
     * If the preceding invoice identifier (BT-25) is not unique, provide this date.
     */
    private OffsetDateTime precedingInvoiceIssueDate;

    /**
     * Creates a new, empty instance of this class.
     */
    public PrecedingInvoiceReference() {
    }

    /**
     * Creates a new, empty instance of this class.
     *
     * @return a new, empty instance
     */
    public static PrecedingInvoiceReference create() {
        return new PrecedingInvoiceReference();
    }

    /**
     * Sets the {@link #precedingInvoiceReference}.
     */
    public PrecedingInvoiceReference precedingInvoiceReference(String precedingInvoiceReference) {
        this.precedingInvoiceReference = precedingInvoiceReference;
        return this;
    }

    /**
     * Sets the {@link #precedingInvoiceIssueDate}.
     */
    public PrecedingInvoiceReference precedingInvoiceIssueDate(OffsetDateTime precedingInvoiceIssueDate) {
        this.precedingInvoiceIssueDate = precedingInvoiceIssueDate;
        return this;
    }

    /**
     * Gets the {@link #precedingInvoiceReference}.
     */
    public String getPrecedingInvoiceReference() {
        return precedingInvoiceReference;
    }

    /**
     * Gets the {@link #precedingInvoiceIssueDate}.
     */
    public OffsetDateTime getPrecedingInvoiceIssueDate() {
        return precedingInvoiceIssueDate;
    }
}
