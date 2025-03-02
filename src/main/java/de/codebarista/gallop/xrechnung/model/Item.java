package de.codebarista.gallop.xrechnung.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.With;

import java.math.BigDecimal;
import java.util.List;

/**
 * Invoice Line (BG-25)
 */
@Builder
@With
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    @Singular
    private List<ItemAttribute> itemAttributes;
}
