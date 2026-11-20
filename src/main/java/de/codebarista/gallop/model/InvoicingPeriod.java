package de.codebarista.gallop.model;

import java.time.OffsetDateTime;

/**
 * A period with a start date and an end date.<br>
 * On the {@link Invoice} it is the invoicing period (BG-14), on an {@link Item} the invoice line period (BG-26).
 * <p>
 * If the period is given, at least one of the two dates must be set (BR-CO-19, BR-CO-20), and the end date must not
 * be before the start date (BR-29, BR-30).
 */
public class InvoicingPeriod {

    /**
     * The date when the invoicing period starts (BT-73), or the invoice line period starts (BT-134)
     */
    private OffsetDateTime startDate;

    /**
     * The date when the invoicing period ends (BT-74), or the invoice line period ends (BT-135)
     */
    private OffsetDateTime endDate;

    /**
     * Creates a new, empty instance of this class.
     */
    public InvoicingPeriod() {
    }

    /**
     * Creates a new, empty instance of this class.
     *
     * @return a new, empty instance
     */
    public static InvoicingPeriod create() {
        return new InvoicingPeriod();
    }

    /**
     * Sets the {@link #startDate}.
     */
    public InvoicingPeriod startDate(OffsetDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * Sets the {@link #endDate}.
     */
    public InvoicingPeriod endDate(OffsetDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    /**
     * Gets the {@link #startDate}.
     */
    public OffsetDateTime getStartDate() {
        return startDate;
    }

    /**
     * Gets the {@link #endDate}.
     */
    public OffsetDateTime getEndDate() {
        return endDate;
    }
}
