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

    private ItemAttribute() {
    }

    private ItemAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Creates a new item attribute instance with the given {@link #name} and {@link #value}.
     *
     * @param name  item attribute name
     * @param value item attribute value
     * @return a new item attribute instance with the given name and value
     */
    public static ItemAttribute of(String name, String value) {
        return new ItemAttribute(name, value);
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

    /**
     * Creates a new instance that is a copy of this object.
     * <p>
     * All field values from this instance are copied to the new one.
     * The returned object is equal to this one if no further modifications are made.
     *
     * @return a new instance with the same field values as this instance
     */
    public ItemAttribute copy() {
        return new ItemAttribute(this.name, this.value);
    }
}
