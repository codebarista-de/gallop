package de.codebarista.gallop.xrechnung.model;

/**
 * Direct Debit (BG-19)
 */
public class DirectDebit {
    /**
     * Mandate reference identifier (BT-89)
     */
    private String mandateReference;

    /**
     * Bank assigned creditor identifier (BT-90)
     */
    private String creditorId;

    /**
     * Debited account identifier (BT-91)
     */
    private String debitedAccountIban;

    /**
     * Creates a new, empty instance of this class.
     */
    public DirectDebit() {
    }

    /**
     * Creates a new, empty instance of this class.
     *
     * @return a new, empty instance
     */
    public static DirectDebit create() {
        return new DirectDebit();
    }

    /**
     * Sets the {@link #mandateReference}.
     */
    public DirectDebit mandateReference(String mandateReference) {
        this.mandateReference = mandateReference;
        return this;
    }

    /**
     * Sets the {@link #creditorId}.
     */
    public DirectDebit creditorId(String creditorId) {
        this.creditorId = creditorId;
        return this;
    }

    /**
     * Sets the {@link #debitedAccountIban}.
     */
    public DirectDebit debitedAccountIban(String debitedAccountIban) {
        this.debitedAccountIban = debitedAccountIban;
        return this;
    }

    /**
     * Gets the {@link #mandateReference}.
     */
    public String getMandateReference() {
        return mandateReference;
    }

    /**
     * Gets the {@link #creditorId}.
     */
    public String getCreditorId() {
        return creditorId;
    }

    /**
     * Gets the {@link #debitedAccountIban}.
     */
    public String getDebitedAccountIban() {
        return debitedAccountIban;
    }
}
