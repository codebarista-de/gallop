package de.codebarista.gallop.xrechnung.model;

/**
 * Payment Card Information (BG-18)
 */
public class PaymentCardInformation {
    /**
     * Payment card primary account number (BT-87)
     */
    private String accountNumber;

    /**
     * Payment card holder name (BT-88)
     */
    private String cardHolderName;

    private PaymentCardInformation() {
    }

    /**
     * Creates a new, empty instance of this class.
     *
     * @return a new, empty instance
     */
    public static PaymentCardInformation create() {
        return new PaymentCardInformation();
    }

    /**
     * Sets the {@link #accountNumber}.
     */
    public PaymentCardInformation accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    /**
     * Sets the {@link #cardHolderName}.
     */
    public PaymentCardInformation cardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
        return this;
    }

    /**
     * Gets the {@link #accountNumber}.
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Gets the {@link #cardHolderName}.
     */
    public String getCardHolderName() {
        return cardHolderName;
    }
}
