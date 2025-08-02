package de.codebarista.gallop.xrechnung.model;

import java.math.BigDecimal;

/**
 * Represents an entity that has a net amount.
 * <p>
 * This interface defines a method to retrieve the net amount of an object.
 * </p>
 */
public interface NetAmount<T> {
    /**
     * Returns the net amount.
     *
     * @return the net amount as a {@link BigDecimal}
     */
    BigDecimal getNetAmount();

    /**
     * Sets the net amount.
     *
     * @param amount the net amount
     */
    T netAmount(BigDecimal amount);

    /**
     * Returns the net vat rate.
     *
     * @return the vat rate as a {@link BigDecimal}
     */
    BigDecimal getVatRate();
}
