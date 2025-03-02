package de.codebarista.gallop.xrechnung.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Seller/Buyer Postal Address (BG-5/BG-8) and Deliver to Address (BG-15)
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostalAddress {
    /**
     * Seller/Buyer address line 1 (BT-35/BT-50)
     * <p>
     * First line of the address. Usually the street name and house number.
     */
    private String addressLineOne;

    /**
     * Seller/Buyer address line 2 (BT-36/BT-51)
     */
    private String addressLineTwo;

    /**
     * Seller/Buyer address line 3 (BT-162/BT-163)
     */
    private String addressLineThree;

    /**
     * Seller/Buyer city (BT-37/BT-52)
     */
    private String city;

    /**
     * Seller/Buyer post code (BT-38/BT-53)
     */
    private String zipCode;

    /**
     * Seller/Buyer country code (BT-40/BT-55)
     */
    private String countryIsoCode;
}
