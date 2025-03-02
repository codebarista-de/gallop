package de.codebarista.gallop.xrechnung.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Item Attributes (BG-32)
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
