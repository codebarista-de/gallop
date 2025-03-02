package de.codebarista.gallop.xrechnung.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;

/**
 * Line Vat Information (BG-30) and Vat Breakdown (BG-23)
 */
@Builder
@Getter
@With
@NoArgsConstructor
@AllArgsConstructor
public class Vat {
    /**
     * VAT rate (BT-152/BT-119)
     */
    private BigDecimal rate;

    /**
     * VAT category code (BT-151/BT-118)<br>
     * See {@linkplain  TaxCategory}
     */
    private TaxCategory category;

    /**
     * VAT category taxable amount (BT-116)
     */
    private BigDecimal taxableAmount;

    /**
     * VAT category tax amount (BT-117)
     */
    private BigDecimal taxAmount;

    /**
     * VAT exemption reason text (BT-120)<br>
     * For reverse-charge (tax category AE) enter „Umkehrung der Steuerschuldnerschaft“ or equivalent reason text
     * in invoice language. Optional.
     */
    private String vatExemptionReasonText;

    /**
     * Vat exemption reason code (BT-121)<br>
     * A code as specified in VATEX "VAT exemption reason code list". Optional.
     */
    private String vatExemptionReasonCode;
}
