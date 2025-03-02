package de.codebarista.gallop.xrechnung.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Direct Debit (BG-19)
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DirectDebit {
    /**
     * Mandate reference identifier (BT-89)
     */
    private String mandateReference;

    /**
     * Bank assigned creditor identifier (BT-90)
     */
    private String creditorId;

    /**
     * Debited account identifier (BT-91)
     */
    private String debitedAccountIban;
}
