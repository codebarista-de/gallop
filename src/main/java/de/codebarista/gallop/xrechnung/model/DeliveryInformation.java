package de.codebarista.gallop.xrechnung.model;

import java.time.OffsetDateTime;

/**
 * Delivery Information (BG-13)
 */
public class DeliveryInformation {

    /**
     * Deliver to party name (BT-70)
     */
    private String name;

    /**
     * Deliver to Address (BG-15)
     */
    private PostalAddress deliveryAddress;

    /**
     * Date on which the delivery actually takes place or the service is actually provided (BT-72)
     */
    private OffsetDateTime actualDeliveryDate;

    /**
     * Creates a new, empty instance of this class.
     */
    public DeliveryInformation() {
    }

    private DeliveryInformation(String name, PostalAddress deliveryAddress, OffsetDateTime actualDeliveryDate) {
        this.name = name;
        this.deliveryAddress = deliveryAddress;
        this.actualDeliveryDate = actualDeliveryDate;
    }

    /**
     * Creates a new, empty instance of this class.
     *
     * @return a new, empty instance
     */
    public static DeliveryInformation create() {
        return new DeliveryInformation();
    }

    /**
     * Sets the {@link #name}.
     */
    public DeliveryInformation name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@link #deliveryAddress}.
     */
    public DeliveryInformation deliveryAddress(PostalAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    /**
     * Sets the {@link #actualDeliveryDate}.
     */
    public DeliveryInformation actualDeliveryDate(OffsetDateTime actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
        return this;
    }

    /**
     * Gets the {@link #name}.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the {@link #deliveryAddress}.
     */
    public PostalAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Gets the {@link #actualDeliveryDate}.
     */
    public OffsetDateTime getActualDeliveryDate() {
        return actualDeliveryDate;
    }
}
