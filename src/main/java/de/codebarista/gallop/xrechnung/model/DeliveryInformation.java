package de.codebarista.gallop.xrechnung.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.OffsetDateTime;

/**
 * Delivery Information (BG-13)
 */
@Builder
@Getter
@With
@NoArgsConstructor
@AllArgsConstructor
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
}
