package de.codebarista.gallop.xrechnung.model;

/**
 * Seller/Buyer Contact (BG-6/BG-9)
 */
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

    public Contact() {
    }

    /**
     * Creates a new, empty instance of this class.
     *
     * @return a new, empty instance
     */
    public static Contact create() {
        return new Contact();
    }

    /**
     * Sets the {@link #name}.
     */
    public Contact name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@link #phone}.
     */
    public Contact phone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * Sets the {@link #email}.
     */
    public Contact email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Gets the {@link #name}.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the {@link #phone}.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets the {@link #email}.
     */
    public String getEmail() {
        return email;
    }
}
