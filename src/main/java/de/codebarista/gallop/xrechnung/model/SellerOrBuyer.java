package de.codebarista.gallop.xrechnung.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Seller/Buyer (BG-4/BG-7)
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SellerOrBuyer {
    /**
     * Seller/Buyer name (BT-27/BT-44)
     * <p>
     * The official/legal name under which the seller/buyer can be found.
     */
    private String name;
    /**
     * Seller/Buyer trading name (BT-28/BT-45)
     * <p>
     * Optional name under which the seller/buyer is known if different from the official/legal name
     */
    private String tradingName;

    /**
     * Seller/Buyer VAT identifier (BT-31/BT-48)
     */
    private String vatId;

    /**
     * Seller/Buyer electronic address (BT-34/BT-49)
     */
    private String electronicAddress;

    /**
     * Seller/Buyer electronic address/Scheme identifier (BT-34/BT-49)
     * <p>
     * Electronic Address Scheme code for the {@linkplain  #electronicAddress}
     * <p>
     * See <a href="https://www.xrepository.de/details/urn:xoev-de:kosit:codeliste:eas_5">EAS code list</a>
     */
    private String electronicAddressScheme;

    /**
     * Seller/Buyer Postal Address (BG-5/BG-8)<br>
     * Seller Postal Address (BG-5) is mandatory.<br>
     * Buyer Postal Address (BG-8) is mandatory.
     */
    private PostalAddress address;

    /**
     * Seller/Buyer Contact Address (BG-6/BG-9)<br>
     * Seller contact (BG-6) is mandatory.<br>
     * Buyer contact (BG-9) is optional.
     */
    private Contact contact;
}
