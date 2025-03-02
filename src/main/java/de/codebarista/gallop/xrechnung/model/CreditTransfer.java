package de.codebarista.gallop.xrechnung.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Credit Transfer (BG-17)
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreditTransfer {
    /**
     * Payment account identifier (BT-84)
     */
    private String iban;

    /**
     * Payment account name (BT-85)
     */
    private String accountName;

    /**
     * Payment service provider identifier (BT-86)
     */
    private String bic;
}
