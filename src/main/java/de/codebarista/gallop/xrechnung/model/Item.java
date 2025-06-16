package de.codebarista.gallop.xrechnung.model;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Invoice Line (BG-25)
 */
public class Item implements NetAmount {

    /**
     * Invoice line identifier (BT-126)
     */
    private Long id;

    /**
     * Invoiced quantity (BT-129)
     */
    private Long quantity;

    /**
     * Invoiced quantity unit of measure code (BT-130)<br>
     * See {@linkplain UnitCode}
     */
    private String unitCode;

    /**
     * Invoice line net amount (BT-131)
     */
    private BigDecimal itemTotalNetAmount;

    /**
     * Returns the invoice line net amount (BT-131)
     */
    @Override
    public BigDecimal getNetAmount() {
        return itemTotalNetAmount;
    }

    /**
     * Item name (BT-153)
     */
    private String name;

    /**
     * Item description (BT-154)
     */
    private String description;

    /**
     * Item net price (BT-146)<br>
     * Must not be negative.
     */
    private BigDecimal unitPrice;

    /**
     * Line Vat Information (BG-30)
     */
    private Vat vat;

    /**
     * Item Sellers identifier (BT-155)
     */
    private String sellerAssignedId;

    /**
     * Item attributes (BG-32)
     */
    private List<ItemAttribute> itemAttributes = new ArrayList<>();

    private Item() {
    }

    private Item(Long id, Long quantity, String unitCode, BigDecimal itemTotalNetAmount, String name,
                 String description, BigDecimal unitPrice, Vat vat, String sellerAssignedId,
                 List<ItemAttribute> itemAttributes) {
        this.id = id;
        this.quantity = quantity;
        this.unitCode = unitCode;
        this.itemTotalNetAmount = itemTotalNetAmount;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.vat = vat;
        this.sellerAssignedId = sellerAssignedId;
        this.itemAttributes = itemAttributes;
    }

    /**
     * Creates a new, empty instance of this class.
     *
     * @return a new, empty instance
     */
    public static Item create() {
        return new Item();
    }

    /**
     * Sets the {@link #id}
     */
    public Item id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the {@link #quantity}
     */
    public Item quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * Sets the {@link #unitCode}
     */
    public Item unitCode(String unitCode) {
        this.unitCode = unitCode;
        return this;
    }

    /**
     * Sets the {@link #itemTotalNetAmount}
     */
    public Item itemTotalNetAmount(BigDecimal itemTotalNetAmount) {
        this.itemTotalNetAmount = itemTotalNetAmount;
        return this;
    }

    /**
     * Sets the {@link #name}
     */
    public Item name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@link #description}
     */
    public Item description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the {@link #unitPrice}
     */
    public Item unitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    /**
     * Sets the {@link #vat}
     */
    public Item vat(Vat vat) {
        this.vat = vat;
        return this;
    }

    /**
     * Sets the {@link #sellerAssignedId}
     */
    public Item sellerAssignedId(String sellerAssignedId) {
        this.sellerAssignedId = sellerAssignedId;
        return this;
    }

    /**
     * Adds the given object to the list of {@link #itemAttributes}
     */
    public Item addItemAttribute(ItemAttribute itemAttribute) {
        this.itemAttributes.add(itemAttribute);
        return this;
    }

    /**
     * Replaces the current list of {@link #itemAttributes} by the given one
     */
    public Item itemAttributes(List<ItemAttribute> itemAttributes) {
        this.itemAttributes = new ArrayList<>(itemAttributes);
        return this;
    }

    /**
     * Clears the list of Item Attributes (BG-32). See {@link #itemAttributes}
     */
    public void clearItemAttributes() {
        itemAttributes.clear();
    }

    /**
     * Gets the {@link #id}.
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the {@link #quantity}.
     */
    public Long getQuantity() {
        return quantity;
    }

    /**
     * Gets the {@link #unitCode}.
     */
    public String getUnitCode() {
        return unitCode;
    }

    /**
     * Gets the {@link #itemTotalNetAmount}.
     */
    public BigDecimal getItemTotalNetAmount() {
        return itemTotalNetAmount;
    }

    /**
     * Gets the {@link #name}.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the {@link #description}.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the {@link #unitPrice}.
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * Gets the {@link #vat}.
     */
    public Vat getVat() {
        return vat;
    }

    /**
     * Gets the {@link #sellerAssignedId}.
     */
    public String getSellerAssignedId() {
        return sellerAssignedId;
    }

    /**
     * Gets the {@link #itemAttributes}.
     */
    public List<ItemAttribute> getItemAttributes() {
        return Collections.unmodifiableList(itemAttributes);
    }

    /**
     * Creates a new instance that is a copy of this object.
     * <p>
     * All field values from this instance are copied to the new one.
     * The returned object is equal to this one if no further modifications are made.
     *
     * @return a new instance with the same field values as this instance
     */
    public Item copy() {
        return new Item(this.id, this.quantity, this.unitCode, this.itemTotalNetAmount, this.name, this.description,
                this.unitPrice, this.vat.copy(), this.sellerAssignedId,
                this.itemAttributes.stream().map(ItemAttribute::copy).toList());
    }
}
