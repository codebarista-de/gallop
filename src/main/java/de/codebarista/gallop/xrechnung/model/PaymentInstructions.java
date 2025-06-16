package de.codebarista.gallop.xrechnung.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Payment Instructions (BG-16)
 */
public class PaymentInstructions {
    /**
     * Payment means type code (BT-81)<br>
     * See {@linkplain PaymentCode}
     */
    private String meansType;

    /**
     * Payment means text (BT-82)
     */
    private String meansText;

    /**
     * Remittance information (BT-83)
     */
    private String remittanceInfo;

    /**
     * Payment terms (BT-20)
     * <p>
     * This field is actually a property of the root invoice element.
     * It more practical to have it here as it's value depends on the payment instructions
     */
    private String paymentTerms;

    /**
     * Credit Transfer (BG-17)
     */
    final private List<CreditTransfer> creditTransfers = new ArrayList<>();

    /**
     * Payment Card Information (BG-18)
     */
    private PaymentCardInformation paymentCardInformation;

    /**
     * Direct Debit (BG-19)
     */
    private DirectDebit directDebit;

    private PaymentInstructions() {
    }

    /**
     * Creates a new, empty instance of this class.
     *
     * @return a new, empty instance
     */
    public static PaymentInstructions create() {
        return new PaymentInstructions();
    }

    /**
     * Sets the {@link #meansType}.
     */
    public PaymentInstructions meansType(String meansType) {
        this.meansType = meansType;
        return this;
    }

    /**
     * Sets the {@link #meansText}.
     */
    public PaymentInstructions meansText(String meansText) {
        this.meansText = meansText;
        return this;
    }

    /**
     * Sets the {@link #remittanceInfo}.
     */
    public PaymentInstructions remittanceInfo(String remittanceInfo) {
        this.remittanceInfo = remittanceInfo;
        return this;
    }

    /**
     * Sets the {@link #paymentTerms}.
     */
    public PaymentInstructions paymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
        return this;
    }

    /**
     * Adds the given object to the list of {@link #creditTransfers}
     */
    public PaymentInstructions creditTransfer(CreditTransfer creditTransfer) {
        this.creditTransfers.add(creditTransfer);
        return this;
    }

    /**
     * Clears the {@link #creditTransfers} list
     */
    public void clearCreditTransfer() {
        creditTransfers.clear();
    }

    /**
     * Sets the {@link #paymentCardInformation}.
     */
    public PaymentInstructions paymentCardInformation(PaymentCardInformation paymentCardInformation) {
        this.paymentCardInformation = paymentCardInformation;
        return this;
    }

    /**
     * Sets the {@link #directDebit}.
     */
    public PaymentInstructions directDebit(DirectDebit directDebit) {
        this.directDebit = directDebit;
        return this;
    }

    /**
     * Gets the {@link #meansType}.
     */
    public String getMeansType() {
        return meansType;
    }

    /**
     * Gets the {@link #meansText}.
     */
    public String getMeansText() {
        return meansText;
    }

    /**
     * Gets the {@link #remittanceInfo}.
     */
    public String getRemittanceInfo() {
        return remittanceInfo;
    }

    /**
     * Gets the {@link #paymentTerms}.
     */
    public String getPaymentTerms() {
        return paymentTerms;
    }

    /**
     * Gets the {@link #creditTransfers}.
     */
    public List<CreditTransfer> getCreditTransfers() {
        return Collections.unmodifiableList(creditTransfers);
    }

    /**
     * Gets the {@link #paymentCardInformation}.
     */
    public PaymentCardInformation getPaymentCardInformation() {
        return paymentCardInformation;
    }

    /**
     * Gets the {@link #directDebit}.
     */
    public DirectDebit getDirectDebit() {
        return directDebit;
    }
}
