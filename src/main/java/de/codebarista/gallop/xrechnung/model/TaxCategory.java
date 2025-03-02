package de.codebarista.gallop.xrechnung.model;

/**
 * Subset of Duty or tax or fee category code that may be used in a XRechnung
 * <p>
 * See <a href="https://www.xrepository.de/details/urn:xoev-de:kosit:codeliste:untdid.5305_3">UNTDID 5305</a>
 * <p>
 * The values in this class are an exhaustive enumeration according to the specification.
 */
public enum TaxCategory {
    /**
     * regular and reduced tax rate (regulärer und ermäßigten Umsatzsteuersatz)
     */
    STANDARD_RATE("S"),

    /**
     * Zero rated sale (Umsatzsteuersatz 0 %).<br>
     * This should be treated the same as the other VAT rates. This applies to all VAT-relevant groups and elements.
     */
    ZERO_RATED_GOODS("Z"),

    /**
     * Exempted from VAT (Kein Ausweis der Umsatzsteuer)<br>
     * The VAT exemption must be justified in a free-text description (BT-120: VAT exemption reason text).
     */
    EXEMPT_FROM_TAX("E"),

    /**
     * Reverse-Charge (Umkehr der Steuerschuldnerschaft)<br>
     * At the document level, the VAT ID of the buyer (Buyer VAT Identifier) and the seller (Seller VAT Identifier)
     * must be provided. The VAT rate must be specified as 0%. The VAT exemption must be justified in a free-text
     * description, referencing the reverse-charge mechanism (if applicable, in the respective local language).
     */
    REVERSE_CHARGE("AE"),

    /**
     * VAT exempt for EEA intra-community supply of goods and services<br>
     * At the document level, the VAT ID of the buyer (Buyer VAT Identifier) and the seller (Seller VAT Identifier)
     * must be provided. The VAT exemption must be justified in a free-text description with "intra-Community supply"
     * (if applicable, in the respective local language). Proof of delivery must be provided,
     * including the destination country (DELIVERY INFORMATION group) and the delivery date.
     */
    INTRA_COMMUNITY_SUPPLY("K"),

    /**
     * Free export item, tax not charged
     */
    FREE_EXPORT_ITEM("G"),

    /**
     * Not subject to VAT (Keine Anwendung des Umsatzsteuerrechts)<br>
     * If a transaction is not subject to VAT regulations, the entire invoice is not subject to VAT regulations.
     */
    SERVICES_OUTSIDE_SCOPE_OF_TAX("O"),

    /**
     * Canary Islands general indirect tax
     */
    CANARY_ISLANDS_GENERAL_INDIRECT_TAX("L"),

    /**
     * Tax for production, services and importation in Ceuta and Melilla
     */
    CEUTA_AND_MELILLA_TAX("M");

    private final String categoryCode;

    TaxCategory(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    /**
     * Returns the tax category code that should be used in the XML writer.
     *
     * @return the tax category code as a string.
     */
    public String getCategoryCode() {
        return categoryCode;
    }

    /**
     * Retrieves the corresponding {@link TaxCategory} enum value based on the provided category code.
     *
     * @param category the tax category code as a string.
     * @return the matching {@link TaxCategory} instance.
     * @throws IllegalArgumentException if no matching tax category is found.
     */
    public static TaxCategory fromCategory(String category) {
        for (TaxCategory taxCategory : values()) {
            if (taxCategory.categoryCode.equalsIgnoreCase(category)) {
                return taxCategory;
            }
        }
        throw new IllegalArgumentException("Unknown category: " + category);
    }
}
