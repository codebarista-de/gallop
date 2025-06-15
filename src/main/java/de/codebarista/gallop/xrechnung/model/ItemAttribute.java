package de.codebarista.gallop.xrechnung.model;

/**
 * Item Attributes (BG-32)
 */
public class ItemAttribute {
    /**
     * Item attribute name (BT-160)
     * <p>
     * Name of the item attribute like "Color"
     */
    private String name;

    /**
     * Item attribute value (BT-161)
     * <p>
     * Value of the item attribute like "red"
     */
    private String value;

    public ItemAttribute() {
    }

    /**
     * Creates a new, empty instance of this class.
     *
     * @return a new, empty instance
     */
    public static ItemAttribute create() {
        return new ItemAttribute();
    }

    /**
     * Sets the {@link #name}.
     */
    public ItemAttribute name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@link #value}.
     */
    public ItemAttribute value(String value) {
        this.value = value;
        return this;
    }

    /**
     * Gets the {@link #name}.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the {@link #value}.
     */
    public String getValue() {
        return value;
    }

}
