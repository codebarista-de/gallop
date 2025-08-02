package de.codebarista.gallop.xrechnung.model;

import java.math.BigDecimal;

/**
 * Document Level Allowances (BG-20)
 */
public class Allowance implements NetAmount<Allowance> {
    /**
     * Document level allowance amount (BT-92). The actual amount without taxes.<br>
     * <b>Must be 0 or a positive value.</b>
     */
    private BigDecimal netAmount;

    /**
     * Document level allowance VAT category code (BT-95)
     */
    private TaxCategory vatCategory;

    /**
     * Document level allowance VAT rate (BT-96)
     */
    private BigDecimal vatRate;

    /**
     * Document level allowance reason (BT-97)
     */
    private String reason;

    /**
     * Creates a new, empty instance of this class.
     */
    public Allowance() {
    }

    /**
     * Creates a new, empty instance of this class.
     *
     * @return a new, empty instance
     */
    public static Allowance create() {
        return new Allowance();
    }

    /**
     * Sets the {@link #netAmount}.
     */
    @Override
    public Allowance netAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
        return this;
    }

    /**
     * Sets the {@link #vatCategory}.
     */
    public Allowance vatCategory(TaxCategory vatCategory) {
        this.vatCategory = vatCategory;
        return this;
    }

    /**
     * Sets the {@link #vatRate}.
     */
    public Allowance vatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
        return this;
    }

    /**
     * Sets the {@link #reason}.
     */
    public Allowance reason(String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    public BigDecimal getNetAmount() {
        return netAmount;
    }

    /**
     * Gets the {@link #vatCategory}.
     */
    public TaxCategory getVatCategory() {
        return vatCategory;
    }

    /**
     * Gets the {@link #vatRate}.
     */
    @Override
    public BigDecimal getVatRate() {
        return vatRate;
    }

    /**
     * Gets the {@link #reason}.
     */
    public String getReason() {
        return reason;
    }
}
