package de.codebarista.gallop.model;

/**
 * Invoice Note (BG-1)
 */
public class InvoiceNote {
    /**
     * Invoice note (BT-22)
     * <p>
     * A text annotation containing unstructured information that is relevant to the invoice as a whole.
     * If necessary, details regarding the retention obligation pursuant to § 14 Section 4 No. 9
     * of the German VAT Act (UStG) can be included here.
     * <p>
     * In the case of an already invoiced bill, for example, the reason for the correction can be stated here.
     */
    private String note;

    /**
     * Invoice note subject code (BT-21)
     * <p>
     * The subject of the invoice note, from the UNTDID 4451 code list.
     * <p>
     * For example, French invoices must contain notes with the subject codes {@code PMT} (flat recovery fee),
     * {@code PMD} (late-payment penalties) and {@code AAB} (early-payment discount).
     * <p>
     * No subject code is written if this is null or empty.
     */
    private String subjectCode;

    /**
     * Creates a new, empty instance of this class.
     */
    public InvoiceNote() {
    }

    /**
     * Creates a new, empty instance of this class.
     *
     * @return a new, empty instance
     */
    public static InvoiceNote create() {
        return new InvoiceNote();
    }

    /**
     * Sets the {@link #note}.
     */
    public InvoiceNote note(String note) {
        this.note = note;
        return this;
    }

    /**
     * Sets the {@link #subjectCode}.
     */
    public InvoiceNote subjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
        return this;
    }

    /**
     * Gets the {@link #note}.
     */
    public String getNote() {
        return note;
    }

    /**
     * Gets the {@link #subjectCode}.
     */
    public String getSubjectCode() {
        return subjectCode;
    }
}
