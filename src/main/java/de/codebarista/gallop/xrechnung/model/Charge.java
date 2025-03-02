package de.codebarista.gallop.xrechnung.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;

/**
 * Document Level Charges (BG-21)
 */
@Builder
@With
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Charge implements NetAmount {
    /**
     * Document level charge amount (BT-99). The actual amount without taxes.<br>
     * <b>Must be 0 or a positive value.</b>
     */
    public BigDecimal netAmount;

    /**
     * Document level charge VAT category code (BT-102)
     */
    public TaxCategory vatCategory;

    /**
     * Document level charge VAT rate (BT-103)
     */
    public BigDecimal vatRate;

    /**
     * Document level charge reason (BT-104)
     */
    public String reason;
}
