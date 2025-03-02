package de.codebarista.gallop.xrechnung.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Seller/Buyer Contact (BG-6/BG-9)
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    /**
     * Seller/Buyer contact point (BT-41/BT-56)
     */
    private String name;

    /**
     * Seller/Buyer contact telephone number (BT-42/BT-57)
     */
    private String phone;

    /**
     * Seller/Buyer contact email address (BT-43/BT-58)
     */
    private String email;
}
