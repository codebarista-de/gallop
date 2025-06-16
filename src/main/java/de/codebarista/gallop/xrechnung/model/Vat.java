package de.codebarista.gallop.xrechnung.model;

import java.math.BigDecimal;

/**
 * Line Vat Information (BG-30) and Vat Breakdown (BG-23)
 */
public class Vat {
    /**
     * VAT rate (BT-152/BT-119)
     */
    private BigDecimal rate;

    /**
     * VAT category code (BT-151/BT-118)<br>
     * See {@linkplain  TaxCategory}
     */
    private TaxCategory category;

    /**
     * VAT category taxable amount (BT-116)
     */
    private BigDecimal taxableAmount;

    /**
     * VAT category tax amount (BT-117)
     */
    private BigDecimal taxAmount;

    /**
     * VAT exemption reason text (BT-120)<br>
     * For reverse-charge (tax category AE) enter „Umkehrung der Steuerschuldnerschaft“ or equivalent reason text
     * in invoice language. Optional.
     */
    private String vatExemptionReasonText;

    /**
     * Vat exemption reason code (BT-121)<br>
     * A code as specified in VATEX "VAT exemption reason code list". Optional.
     */
    private String vatExemptionReasonCode;

    private Vat() {
    }

    private Vat(BigDecimal rate, TaxCategory category, BigDecimal taxableAmount, BigDecimal taxAmount,
                String vatExemptionReasonText, String vatExemptionReasonCode) {
        this.rate = rate;
        this.category = category;
        this.taxableAmount = taxableAmount;
        this.taxAmount = taxAmount;
        this.vatExemptionReasonText = vatExemptionReasonText;
        this.vatExemptionReasonCode = vatExemptionReasonCode;
    }

    /**
     * Creates a new, empty instance of this class.
     *
     * @return a new, empty instance
     */
    public static Vat create() {
        return new Vat();
    }

    /**
     * Sets the {@link #rate}.
     */
    public Vat rate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    /**
     * Sets the {@link #category}.
     */
    public Vat category(TaxCategory category) {
        this.category = category;
        return this;
    }

    /**
     * Sets the {@link #taxableAmount}.
     */
    public Vat taxableAmount(BigDecimal taxableAmount) {
        this.taxableAmount = taxableAmount;
        return this;
    }

    /**
     * Sets the {@link #taxAmount}.
     */
    public Vat taxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    /**
     * Sets the {@link #vatExemptionReasonText}.
     */
    public Vat vatExemptionReasonText(String vatExemptionReasonText) {
        this.vatExemptionReasonText = vatExemptionReasonText;
        return this;
    }

    /**
     * Sets the {@link #vatExemptionReasonCode}.
     */
    public Vat vatExemptionReasonCode(String vatExemptionReasonCode) {
        this.vatExemptionReasonCode = vatExemptionReasonCode;
        return this;
    }

    /**
     * Gets the {@link #rate}.
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * Gets the {@link #category}.
     */
    public TaxCategory getCategory() {
        return category;
    }

    /**
     * Gets the {@link #taxableAmount}.
     */
    public BigDecimal getTaxableAmount() {
        return taxableAmount;
    }

    /**
     * Gets the {@link #taxAmount}.
     */
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    /**
     * Gets the {@link #vatExemptionReasonText}.
     */
    public String getVatExemptionReasonText() {
        return vatExemptionReasonText;
    }

    /**
     * Gets the {@link #vatExemptionReasonCode}.
     */
    public String getVatExemptionReasonCode() {
        return vatExemptionReasonCode;
    }

    /**
     * Creates a new instance that is a copy of this object.
     * <p>
     * All field values from this instance are copied to the new one.
     * The returned object is equal to this one if no further modifications are made.
     *
     * @return a new instance with the same field values as this instance
     */
    public Vat copy() {
        return new Vat(this.rate, this.category, this.taxableAmount, this.taxAmount, this.vatExemptionReasonText,
                this.vatExemptionReasonCode);
    }
}
