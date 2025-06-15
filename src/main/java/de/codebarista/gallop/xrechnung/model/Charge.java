package de.codebarista.gallop.xrechnung.model;

import java.math.BigDecimal;

/**
 * Document Level Charges (BG-21)
 */
public class Charge implements NetAmount {
    /**
     * Document level charge amount (BT-99). The actual amount without taxes.<br>
     * <b>Must be 0 or a positive value.</b>
     */
    private BigDecimal netAmount;

    /**
     * Document level charge VAT category code (BT-102)
     */
    private TaxCategory vatCategory;

    /**
     * Document level charge VAT rate (BT-103)
     */
    private BigDecimal vatRate;

    /**
     * Document level charge reason (BT-104)
     */
    private String reason;

    private Charge() {
    }

    public Charge(BigDecimal netAmount, TaxCategory vatCategory, BigDecimal vatRate, String reason) {
        this.netAmount = netAmount;
        this.vatCategory = vatCategory;
        this.vatRate = vatRate;
        this.reason = reason;
    }

    /**
     * Creates a new, empty instance of this class.
     *
     * @return a new, empty instance
     */
    public static Charge create() {
        return new Charge();
    }

    /**
     * Sets the {@link #netAmount}.
     */
    public Charge netAmount(BigDecimal netAmount) {
        if (netAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Net amount must be zero or positive");
        }
        this.netAmount = netAmount;
        return this;
    }

    /**
     * Sets the {@link #vatCategory}.
     */
    public Charge vatCategory(TaxCategory vatCategory) {
        this.vatCategory = vatCategory;
        return this;
    }

    /**
     * Sets the {@link #vatRate}.
     */
    public Charge vatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
        return this;
    }

    /**
     * Sets the {@link #reason}.
     */
    public Charge reason(String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    public BigDecimal getNetAmount() {
        return null;
    }

    public TaxCategory getVatCategory() {
        return vatCategory;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public String getReason() {
        return reason;
    }

    /**
     * Creates a new instance that is a copy of this object.
     * <p>
     * All field values from this instance are copied to the new one.
     * The returned object is equal to this one if no further modifications are made.
     *
     * @return a new instance with the same field values as this instance
     */
    public Charge copy() {
        return new Charge(this.netAmount, this.vatCategory, this.vatRate, this.reason);
    }
}
