package de.codebarista.gallop.model;

/**
 * Seller/Buyer (BG-4/BG-7)
 */
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
     * Seller/Buyer legal registration identifier (BT-30/BT-47)
     * <p>
     * An identifier issued by an official registration authority that identifies the seller/buyer
     * as a legal entity or legal person.
     */
    private String legalRegistrationIdentifier;

    /**
     * Seller/Buyer legal registration identifier scheme for (BT-30/BT-47)
     * <p>
     * A code from <a href="https://www.xrepository.de/details/urn:xoev-de:kosit:codeliste:icd_5">ISO/IEC 17 6523</a>
     * that describes the schema/type of the legal registration identifier.
     * <p>
     * No schema is set for BT-30 if this is null or empty.
     */
    private String legalRegistrationIdentifierScheme;

    /**
     * Seller/Buyer VAT identifier (BT-31/BT-48)
     */
    private String vatId;

    /**
     * Seller tax registration identifier (BT-32)
     * <p>
     * A local tax identification of the seller (determined by their address)
     * or a reference to their registered tax status.
     * (If applicable, the indication "reverse charge" or the VAT exemption of
     * the invoice issuer should be entered here.)
     * <p>
     * Usually the Tax-ID.
     * <p>
     * Only necessary if VAT identifier (BT-31) is not present.
     */
    private String sellerTaxRegistrationIdentifier;

    /**
     * Seller additional legal information (BT-33)
     * <p>
     * Additional legal information that is relevant for the seller (such as share capital).
     */
    private String sellerAdditionalLegalInfo;

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

    /**
     * Creates a new, empty instance of this class.
     */
    public SellerOrBuyer() {
    }

    /**
     * Creates a new, empty instance of this class.
     *
     * @return a new, empty instance
     */
    public static SellerOrBuyer create() {
        return new SellerOrBuyer();
    }

    /**
     * Sets the {@link #name}.
     */
    public SellerOrBuyer name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@link #tradingName}.
     */
    public SellerOrBuyer tradingName(String tradingName) {
        this.tradingName = tradingName;
        return this;
    }

    /**
     * Sets the {@link #vatId}.
     */
    public SellerOrBuyer vatId(String vatId) {
        this.vatId = vatId;
        return this;
    }

    /**
     * Sets the {@link #sellerTaxRegistrationIdentifier}.
     */
    public SellerOrBuyer sellerTaxRegistrationIdentifier(String taxId) {
        this.sellerTaxRegistrationIdentifier = taxId;
        return this;
    }

    /**
     * Sets the {@link #legalRegistrationIdentifier}.
     */
    public SellerOrBuyer legalRegistrationIdentifier(String legalRegistrationIdentifier) {
        this.legalRegistrationIdentifier = legalRegistrationIdentifier;
        return this;
    }

    /**
     * Sets the {@link #legalRegistrationIdentifierScheme}.
     */
    public SellerOrBuyer legalRegistrationIdentifierScheme(String legalRegistrationIdentifierScheme) {
        this.legalRegistrationIdentifierScheme = legalRegistrationIdentifierScheme;
        return this;
    }

    /**
     * Sets the {@link #sellerAdditionalLegalInfo}.
     */
    public SellerOrBuyer sellerAdditionalLegalInfo(String info) {
        this.sellerAdditionalLegalInfo = info;
        return this;
    }

    /**
     * Sets the {@link #electronicAddress}.
     */
    public SellerOrBuyer electronicAddress(String electronicAddress) {
        this.electronicAddress = electronicAddress;
        return this;
    }

    /**
     * Sets the {@link #electronicAddressScheme}.
     */
    public SellerOrBuyer electronicAddressScheme(String electronicAddressScheme) {
        this.electronicAddressScheme = electronicAddressScheme;
        return this;
    }

    /**
     * Sets the {@link #address}.
     */
    public SellerOrBuyer address(PostalAddress address) {
        this.address = address;
        return this;
    }

    /**
     * Sets the {@link #contact}.
     */
    public SellerOrBuyer contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    /**
     * Gets the {@link #name}.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the {@link #tradingName}.
     */
    public String getTradingName() {
        return tradingName;
    }

    /**
     * Gets the {@link #vatId}.
     */
    public String getVatId() {
        return vatId;
    }

    /**
     * Gets the {@link #sellerTaxRegistrationIdentifier}
     */
    public String getSellerTaxRegistrationIdentifier() {
        return sellerTaxRegistrationIdentifier;
    }

    /**
     * Gets the {@link #legalRegistrationIdentifier}
     */
    public String getLegalRegistrationIdentifier() {
        return legalRegistrationIdentifier;
    }

    /**
     * Gets the {@link #legalRegistrationIdentifierScheme}
     */
    public String getLegalRegistrationIdentifierScheme() {
        return legalRegistrationIdentifierScheme;
    }

    /**
     * Gets the {@link #sellerAdditionalLegalInfo}
     */
    public String getSellerAdditionalLegalInfo() {
        return sellerAdditionalLegalInfo;
    }

    /**
     * Gets the {@link #electronicAddress}.
     */
    public String getElectronicAddress() {
        return electronicAddress;
    }

    /**
     * Gets the {@link #electronicAddressScheme}.
     */
    public String getElectronicAddressScheme() {
        return electronicAddressScheme;
    }

    /**
     * Gets the {@link #address}.
     */
    public PostalAddress getAddress() {
        return address;
    }

    /**
     * Gets the {@link #contact}.
     */
    public Contact getContact() {
        return contact;
    }
}
