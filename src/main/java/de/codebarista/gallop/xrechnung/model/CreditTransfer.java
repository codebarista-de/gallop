package de.codebarista.gallop.xrechnung.model;

/**
 * Credit Transfer (BG-17)
 */
public class CreditTransfer {
    /**
     * Payment account identifier (BT-84)
     */
    private String iban;

    /**
     * Payment account name (BT-85)
     */
    private String accountName;

    /**
     * Payment service provider identifier (BT-86)
     */
    private String bic;

    /**
     * Creates a new, empty instance of this class.
     */
    public CreditTransfer() {
    }

    /**
     * Creates a new, empty instance of this class.
     *
     * @return a new, empty instance
     */
    public static CreditTransfer create() {
        return new CreditTransfer();
    }

    /**
     * Sets the {@link #iban}.
     */
    public CreditTransfer iban(String iban) {
        this.iban = iban;
        return this;
    }

    /**
     * Sets the {@link #accountName}.
     */
    public CreditTransfer accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    /**
     * Sets the {@link #bic}.
     */
    public CreditTransfer bic(String bic) {
        this.bic = bic;
        return this;
    }

    /**
     * Gets the {@link #iban}.
     */
    public String getIban() {
        return iban;
    }

    /**
     * Gets the {@link #accountName}.
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Gets the {@link #bic}.
     */
    public String getBic() {
        return bic;
    }
}
