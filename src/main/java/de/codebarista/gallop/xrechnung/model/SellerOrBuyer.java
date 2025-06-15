package de.codebarista.gallop.xrechnung.model;

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

    private SellerOrBuyer() {
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
