package de.codebarista.gallop.xrechnung.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * XRechnung root element
 */
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
    private final List<Item> items = new ArrayList<>();

    /**
     * VAT Breakdown (BG-23)<br>
     * VAT breakdown by different categories, tax rates, and exemption reasons
     */
    private final List<Vat> vatTotals = new ArrayList<>();

    /**
     * Information about one or more preceding invoice(s) (BG-3)
     */
    private final List<PrecedingInvoiceReference> precedingInvoiceReferences = new ArrayList<>();

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
    private final List<InvoiceNote> invoiceNotes = new ArrayList<>();

    /**
     * Document level allowances (BG-20)
     */
    private final List<Allowance> allowances = new ArrayList<>();

    /**
     * Document level charges (BG-21)
     */
    private final List<Charge> charges = new ArrayList<>();

    public Invoice() {
    }

    /**
     * Creates a new, empty Invoice instance.
     *
     * @return a new, empty Invoice instance
     */
    public static Invoice create() {
        return new Invoice();
    }

    /**
     * Sets the {@link #documentId}
     */
    public Invoice documentId(String documentId) {
        this.documentId = documentId;
        return this;
    }

    /**
     * Sets the {@link #issueDate}
     */
    public Invoice issueDate(OffsetDateTime issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    /**
     * Sets the {@link #documentTypeCode}
     */
    public Invoice documentTypeCode(String documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
        return this;
    }

    /**
     * Sets the {@link #currency}
     */
    public Invoice currency(String currency) {
        this.currency = currency;
        return this;
    }

    /**
     * Sets the {@link #leitwegId}
     */
    public Invoice leitwegId(String leitwegId) {
        this.leitwegId = leitwegId;
        return this;
    }

    /**
     * Sets the {@link #paymentInstructions}
     */
    public Invoice paymentInstructions(PaymentInstructions paymentInstructions) {
        this.paymentInstructions = paymentInstructions;
        return this;
    }

    /**
     * Sets the {@link #seller}
     */
    public Invoice seller(SellerOrBuyer seller) {
        this.seller = seller;
        return this;
    }

    /**
     * Sets the {@link #buyer}
     */
    public Invoice buyer(SellerOrBuyer buyer) {
        this.buyer = buyer;
        return this;
    }

    /**
     * Sets the {@link #deliveryInfo}
     */
    public Invoice deliveryInfo(DeliveryInformation deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
        return this;
    }

    /**
     * Adds all elements from the given list to the list of {@link #items}
     */
    public Invoice items(List<Item> items) {
        this.items.addAll(items);
        return this;
    }

    /**
     * Adds the given object to the list of {@link #items}
     */
    public Invoice item(Item item) {
        this.items.add(item);
        return this;
    }

    /**
     * Clears the {@link #items} list
     */
    public void clearItems() {
        items.clear();
    }

    /**
     * Adds the given object to the list of {@link #vatTotals}
     */
    public Invoice vatTotal(Vat vatTotal) {
        this.vatTotals.add(vatTotal);
        return this;
    }

    /**
     * Adds all elements from the given list to the list of {@link #items}
     */
    public Invoice vatTotals(List<Vat> vatTotals) {
        this.vatTotals.addAll(vatTotals);
        return this;
    }

    /**
     * Clears the {@link #vatTotals} list
     */
    public void clearVatTotals() {
        vatTotals.clear();
    }

    /**
     * Adds the given object to the list of {@link #precedingInvoiceReferences}
     */
    public Invoice precedingInvoiceReference(PrecedingInvoiceReference precedingInvoiceReference) {
        this.precedingInvoiceReferences.add(precedingInvoiceReference);
        return this;
    }

    /**
     * Clears the {@link #precedingInvoiceReferences} list
     */
    public void clearPrecedingInvoiceReferences() {
        precedingInvoiceReferences.clear();
    }

    /**
     * Sets the {@link #lineTotalAmount}
     */
    public Invoice lineTotalAmount(BigDecimal lineTotalAmount) {
        this.lineTotalAmount = lineTotalAmount;
        return this;
    }

    /**
     * Sets the {@link #allowanceTotalAmount}
     */
    public Invoice allowanceTotalAmount(BigDecimal allowanceTotalAmount) {
        this.allowanceTotalAmount = allowanceTotalAmount;
        return this;
    }

    /**
     * Sets the {@link #chargeTotalAmount}
     */
    public Invoice chargeTotalAmount(BigDecimal chargeTotalAmount) {
        this.chargeTotalAmount = chargeTotalAmount;
        return this;
    }

    /**
     * Sets the {@link #taxBasisTotalAmount}
     */
    public Invoice taxBasisTotalAmount(BigDecimal taxBasisTotalAmount) {
        this.taxBasisTotalAmount = taxBasisTotalAmount;
        return this;
    }

    /**
     * Sets the {@link #taxTotalAmount}
     */
    public Invoice taxTotalAmount(BigDecimal taxTotalAmount) {
        this.taxTotalAmount = taxTotalAmount;
        return this;
    }

    /**
     * Sets the {@link #grandTotalAmount}
     */
    public Invoice grandTotalAmount(BigDecimal grandTotalAmount) {
        this.grandTotalAmount = grandTotalAmount;
        return this;
    }

    /**
     * Sets the {@link #duePayableAmount}
     */
    public Invoice duePayableAmount(BigDecimal duePayableAmount) {
        this.duePayableAmount = duePayableAmount;
        return this;
    }

    /**
     * Sets the {@link #salesOrderReference}
     */
    public Invoice salesOrderReference(String salesOrderReference) {
        this.salesOrderReference = salesOrderReference;
        return this;
    }

    /**
     * Adds the given object to the list of {@link #invoiceNotes}
     */
    public Invoice invoiceNote(InvoiceNote invoiceNote) {
        this.invoiceNotes.add(invoiceNote);
        return this;
    }

    /**
     * Clears the {@link #invoiceNotes} list
     */
    public void clearInvoiceNotes() {
        invoiceNotes.clear();
    }

    /**
     * Adds the given object to the list of {@link #allowances}
     */
    public Invoice allowance(Allowance allowance) {
        this.allowances.add(allowance);
        return this;
    }

    /**
     * Clears the {@link #allowances} list
     */
    public void clearAllowances() {
        allowances.clear();
    }

    /**
     * Adds the given object to the list of {@link #charges}
     */
    public Invoice charge(Charge charge) {
        this.charges.add(charge);
        return this;
    }

    /**
     * Clears the {@link #charges} list
     */
    public void clearCharges() {
        charges.clear();
    }

    /**
     * Gets the {@link #documentId}.
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * Gets the {@link #issueDate}.
     */
    public OffsetDateTime getIssueDate() {
        return issueDate;
    }

    /**
     * Gets the {@link #documentTypeCode}.
     */
    public String getDocumentTypeCode() {
        return documentTypeCode;
    }

    /**
     * Gets the {@link #currency}.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Gets the {@link #leitwegId}.
     */
    public String getLeitwegId() {
        return leitwegId;
    }

    /**
     * Gets the {@link #paymentInstructions}.
     */
    public PaymentInstructions getPaymentInstructions() {
        return paymentInstructions;
    }

    /**
     * Gets the {@link #seller}.
     */
    public SellerOrBuyer getSeller() {
        return seller;
    }

    /**
     * Gets the {@link #buyer}.
     */
    public SellerOrBuyer getBuyer() {
        return buyer;
    }

    /**
     * Gets the {@link #deliveryInfo}.
     */
    public DeliveryInformation getDeliveryInfo() {
        return deliveryInfo;
    }

    /**
     * Gets the {@link #items}.
     */
    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Gets the {@link #vatTotals}.
     */
    public List<Vat> getVatTotals() {
        return Collections.unmodifiableList(vatTotals);
    }

    /**
     * Gets the {@link #precedingInvoiceReferences}.
     */
    public List<PrecedingInvoiceReference> getPrecedingInvoiceReferences() {
        return Collections.unmodifiableList(precedingInvoiceReferences);
    }

    /**
     * Gets the {@link #lineTotalAmount}.
     */
    public BigDecimal getLineTotalAmount() {
        return lineTotalAmount;
    }

    /**
     * Gets the {@link #allowanceTotalAmount}.
     */
    public BigDecimal getAllowanceTotalAmount() {
        return allowanceTotalAmount;
    }

    /**
     * Gets the {@link #chargeTotalAmount}.
     */
    public BigDecimal getChargeTotalAmount() {
        return chargeTotalAmount;
    }

    /**
     * Gets the {@link #taxBasisTotalAmount}.
     */
    public BigDecimal getTaxBasisTotalAmount() {
        return taxBasisTotalAmount;
    }

    /**
     * Gets the {@link #taxTotalAmount}.
     */
    public BigDecimal getTaxTotalAmount() {
        return taxTotalAmount;
    }

    /**
     * Gets the {@link #grandTotalAmount}.
     */
    public BigDecimal getGrandTotalAmount() {
        return grandTotalAmount;
    }

    /**
     * Gets the {@link #duePayableAmount}.
     */
    public BigDecimal getDuePayableAmount() {
        return duePayableAmount;
    }

    /**
     * Gets the {@link #salesOrderReference}.
     */
    public String getSalesOrderReference() {
        return salesOrderReference;
    }

    /**
     * Gets the {@link #invoiceNotes}.
     */
    public List<InvoiceNote> getInvoiceNotes() {
        return Collections.unmodifiableList(invoiceNotes);
    }

    /**
     * Gets the {@link #allowances}.
     */
    public List<Allowance> getAllowances() {
        return Collections.unmodifiableList(allowances);
    }

    /**
     * Gets the {@link #charges}.
     */
    public List<Charge> getCharges() {
        return Collections.unmodifiableList(charges);
    }
}
