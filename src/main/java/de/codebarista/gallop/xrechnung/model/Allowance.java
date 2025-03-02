package de.codebarista.gallop.xrechnung.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;

/**
 * Document Level Allowances (BG-20)
 */
@Builder
@With
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Allowance implements NetAmount {
    /**
     * Document level allowance amount (BT-92). The actual amount without taxes.<br>
     * <b>Must be 0 or a positive value.</b>
     */
    public BigDecimal netAmount;

    /**
     * Document level allowance VAT category code (BT-95)
     */
    public TaxCategory vatCategory;

    /**
     * Document level allowance VAT rate (BT-96)
     */
    public BigDecimal vatRate;

    /**
     * Document level allowance reason (BT-97)
     */
    public String reason;
}
