package de.codebarista.gallop.xrechnung.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Payment Card Information (BG-18)
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCardInformation {
    /**
     * Payment card primary account number (BT-87)
     */
    private String accountNumber;

    /**
     * Payment card holder name (BT-88)
     */
    private String cardHolderName;
}
