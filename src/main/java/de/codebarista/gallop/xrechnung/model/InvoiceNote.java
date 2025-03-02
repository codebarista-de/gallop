package de.codebarista.gallop.xrechnung.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Invoice Note (BG-1)
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceNote {
    /**
     * Invoice note (BT-22)
     * <p>
     * A text annotation containing unstructured information that is relevant to the invoice as a whole.
     * If necessary, details regarding the retention obligation pursuant to § 14 Section 4 No. 9
     * of the German VAT Act (UStG) can be included here.
     * <p>
     * In the case of an already invoiced bill, for example, the reason for the correction can be stated here.
     */
    private String note;
}
