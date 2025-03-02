package de.codebarista.gallop.xrechnung.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.With;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * XRechnung root element
 */
@Builder
@Getter
@With
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    /**
     * Invoice number (BT-1)
     */
    private String documentId;

    /**
     * Invoice issue date (BT-2)
     */
    private OffsetDateTime issueDate;

    /**
     * Invoice Type Code (BT-3)<br>
     * See {@linkplain InvoiceType}
     */
    private String documentTypeCode;

    /**
     * Invoice currency code (BT-5)
     */
    private String currency;

    /**
     * BuyerReference (BT-10)<br>
     * Usually a "Leitweg-ID"
     */
    private String leitwegId;

    /**
     * Payment Instructions (BG-16)
     */
    private PaymentInstructions paymentInstructions;

    /**
     * Seller (BG-4)
     */
    private SellerOrBuyer seller;

    /**
     * Seller (BG-7)
     */
    private SellerOrBuyer buyer;

    /**
     * Delivery Information (BG-13)
     */
    private DeliveryInformation deliveryInfo;

    /**
     * Invoice Line (BG-25)<br>
     * Information about individual invoice items
     */
    @Singular
    private List<Item> items;

    /**
     * VAT Breakdown (BG-23)<br>
     * VAT breakdown by different categories, tax rates, and exemption reasons
     */
    @Singular
    private List<Vat> vatTotals;

    /**
     * Information about one or more preceding invoice(s) (BG-3)
     */
    @Singular
    private List<PrecedingInvoiceReference> precedingInvoiceReferences;

    /**
     * Sum of Invoice line net amount (BT-106)
     */
    private BigDecimal lineTotalAmount;

    /**
     * Sum of allowances on document level (BT-107)
     */
    private BigDecimal allowanceTotalAmount;

    /**
     * Sum of charges on document level (BT-108)
     */
    private BigDecimal chargeTotalAmount;

    /**
     * Invoice total amount without VAT (BT-109)
     */
    private BigDecimal taxBasisTotalAmount;

    /**
     * Invoice total VAT amount (BT-110)
     */
    private BigDecimal taxTotalAmount;

    /**
     * Invoice total amount with VAT (BT-112)
     */
    private BigDecimal grandTotalAmount;

    /**
     * Amount due for payment (BT-115)
     */
    private BigDecimal duePayableAmount;

    /**
     * Sales order reference (BT-14)
     */
    private String salesOrderReference;

    /**
     * Invoice notes (BG-1)
     */
    @Singular
    private List<InvoiceNote> invoiceNotes;

    /**
     * Document level allowances (BG-20)
     */
    @Singular
    private List<Allowance> allowances;

    /**
     * Document level charges (BG-21)
     */
    @Singular
    private List<Charge> charges;

}
