package de.codebarista.gallop.xrechnung.model;

import java.math.BigDecimal;

/**
 * Represents an entity that has a net amount.
 * <p>
 * This interface defines a method to retrieve the net amount of an object.
 * </p>
 */
public interface NetAmount {
    /**
     * Returns the net amount.
     *
     * @return the net amount as a {@link BigDecimal}
     */
    BigDecimal getNetAmount();
}
