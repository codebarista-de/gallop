package de.codebarista.gallop.xrechnung.model;

/**
 * Seller/Buyer Postal Address (BG-5/BG-8) and Deliver to Address (BG-15)
 */
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

    /**
     * Creates a new, empty instance of this class.
     */
    public PostalAddress() {
    }

    public PostalAddress(String addressLineOne, String addressLineTwo, String addressLineThree, String city,
                         String zipCode, String countryIsoCode) {
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.addressLineThree = addressLineThree;
        this.city = city;
        this.zipCode = zipCode;
        this.countryIsoCode = countryIsoCode;
    }

    /**
     * Creates a new, empty instance of this class.
     *
     * @return a new, empty instance
     */
    public static PostalAddress create() {
        return new PostalAddress();
    }

    /**
     * Sets the {@link #addressLineOne}.
     */
    public PostalAddress addressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
        return this;
    }

    /**
     * Sets the {@link #addressLineTwo}.
     */
    public PostalAddress addressLineTwo(String addressLineTwo) {
        this.addressLineTwo = addressLineTwo;
        return this;
    }

    /**
     * Sets the {@link #addressLineThree}.
     */
    public PostalAddress addressLineThree(String addressLineThree) {
        this.addressLineThree = addressLineThree;
        return this;
    }

    /**
     * Sets the {@link #city}.
     */
    public PostalAddress city(String city) {
        this.city = city;
        return this;
    }

    /**
     * Sets the {@link #zipCode}.
     */
    public PostalAddress zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    /**
     * Sets the {@link #countryIsoCode}.
     */
    public PostalAddress countryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
        return this;
    }

    /**
     * Gets the {@link #addressLineOne}.
     */
    public String getAddressLineOne() {
        return addressLineOne;
    }

    /**
     * Gets the {@link #addressLineTwo}.
     */
    public String getAddressLineTwo() {
        return addressLineTwo;
    }

    /**
     * Gets the {@link #addressLineThree}.
     */
    public String getAddressLineThree() {
        return addressLineThree;
    }

    /**
     * Gets the {@link #city}.
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets the {@link #zipCode}.
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Gets the {@link #countryIsoCode}.
     */
    public String getCountryIsoCode() {
        return countryIsoCode;
    }
}
