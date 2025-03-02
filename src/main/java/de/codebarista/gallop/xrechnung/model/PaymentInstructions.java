package de.codebarista.gallop.xrechnung.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.List;

/**
 * Payment Instructions (BG-16)
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInstructions {
    /**
     * Payment means type code (BT-81)<br>
     * See {@linkplain PaymentCode}
     */
    private String meansType;

    /**
     * Payment means text (BT-82)
     */
    private String meansText;

    /**
     * Remittance information (BT-83)
     */
    private String remittanceInfo;

    /**
     * Payment terms (BT-20)
     * <p>
     * This field is actually a property of the root invoice element.
     * It more practical to have it here as it's value depends on the payment instructions
     */
    private String paymentTerms;

    /**
     * Credit Transfer (BG-17)
     */
    @Singular
    private List<CreditTransfer> creditTransfers;

    /**
     * Payment Card Information (BG-18)
     */
    private PaymentCardInformation paymentCardInformation;

    /**
     * Direct Debit (BG-19)
     */
    private DirectDebit directDebit;
}
