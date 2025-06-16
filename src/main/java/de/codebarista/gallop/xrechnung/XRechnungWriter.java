package de.codebarista.gallop.xrechnung;

import de.codebarista.gallop.xrechnung.model.Allowance;
import de.codebarista.gallop.xrechnung.model.Charge;
import de.codebarista.gallop.xrechnung.model.Contact;
import de.codebarista.gallop.xrechnung.model.CreditTransfer;
import de.codebarista.gallop.xrechnung.model.DeliveryInformation;
import de.codebarista.gallop.xrechnung.model.Invoice;
import de.codebarista.gallop.xrechnung.model.InvoiceNote;
import de.codebarista.gallop.xrechnung.model.Item;
import de.codebarista.gallop.xrechnung.model.ItemAttribute;
import de.codebarista.gallop.xrechnung.model.PaymentCardInformation;
import de.codebarista.gallop.xrechnung.model.PaymentInstructions;
import de.codebarista.gallop.xrechnung.model.PostalAddress;
import de.codebarista.gallop.xrechnung.model.PrecedingInvoiceReference;
import de.codebarista.gallop.xrechnung.model.SellerOrBuyer;
import de.codebarista.gallop.xrechnung.model.Vat;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Writes a XRechnung XML with the data of an {@linkplain Invoice} object
 * <p>
 * The mapping from the XRechnung specification ID (like BT-82) to the corresponding XML element can be found
 * in the <code>XRechnung-v3.0.2-Syntax-Binding-Extension-UBL.pdf</code> or in the XRechnung bundle at:
 * <code>xrechnung-3.0.2-xrechnung-visualization-2024-06-20/xsl/cii-xr.xsl</code>
 * <p>
 * The writer will always produce valid XML, but it does not guarantee that the generated XRechnung is valid
 * according to the schema and the business rules.
 * E.g. if required data is missing the writer will create an empty element or omit the corresponding xml structure completely.
 * The XRechnung will only be valid if the data in the invoice object is correct.
 */
public class XRechnungWriter {
    private static final String VAT_TYPE_CODE = "VAT";
    private static final String NS_RSM = "urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100";
    private static final String NS_RAM = "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100";
    private static final String NS_UDT = "urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100";
    private static final String NS_QDT = "urn:un:unece:uncefact:data:standard:QualifiedDataType:100";
    private final Invoice invoice;

    /**
     * Constructs a new {@code XRechnungWriter} with the specified invoice.
     *
     * @param invoice the invoice to be written, must not be {@code null}
     */
    public XRechnungWriter(Invoice invoice) {
        Objects.requireNonNull(invoice, "Invoice must not be null");
        this.invoice = invoice;
    }

    /**
     * Convert an invoice to a XRechnung XML
     *
     * @param invoice the Invoice object to serialize to XML, must not be {@code null}
     * @return binary XRechnung XML document
     * @throws XRechnungWriterException if the creation of the XRechnung failed
     */
    public static byte[] generateXRechnungXML(Invoice invoice) {
        Objects.requireNonNull(invoice, "Invoice must not be null");
        var xmlWriter = new XRechnungWriter(invoice);
        try {
            return xmlWriter.getXML();
        } catch (Exception e) {
            throw new XRechnungWriterException("XRechnung creation failed", e);
        }
    }

    /**
     * Generates an XML representation of the invoice in the Cross-Industry Invoice (CII) format.
     * <p>
     * This method constructs an XML document using {@link XmlDocumentBuilder}, setting up the necessary
     * namespaces and elements according to the CII structure.
     * </p>
     *
     * @return a byte array containing the serialized XML document
     * @throws ParserConfigurationException if an error occurs while creating the XML document
     * @throws TransformerException         if an error occurs during XML transformation
     */
    public byte[] getXML() throws ParserConfigurationException, TransformerException {
        var builder = new XmlDocumentBuilder();
        builder.addNamespace("rsm", NS_RSM);
        builder.addNamespace("ram", NS_RAM);
        builder.addNamespace("udt", NS_UDT);
        builder.addNamespace("qdt", NS_QDT);
        Element root = builder.createElement(NS_RSM, "CrossIndustryInvoice");
        builder.setRootElement(root);
        root.appendChild(createExchangedDocumentContext(builder));
        root.appendChild(createExchangedDocument(builder, invoice.getInvoiceNotes()));
        Element tradeTransaction = builder.createElement(NS_RSM, "SupplyChainTradeTransaction");
        root.appendChild(tradeTransaction);
        for (Item lineItem : invoice.getItems()) {
            tradeTransaction.appendChild(createTradeLineItem(lineItem, builder));
        }
        tradeTransaction.appendChild(createTradeHeader(builder));
        tradeTransaction.appendChild(createTradeDelivery(builder));
        tradeTransaction.appendChild(createTradeSettlement(builder));

        return toXml(builder.getDomSource());
    }

    private Element createExchangedDocumentContext(XmlDocumentBuilder builder) {
        Element exchangedDocumentContext = builder.createElement(NS_RSM, "ExchangedDocumentContext");
        Element businessContextParam = builder.createElement(NS_RAM, "BusinessProcessSpecifiedDocumentContextParameter");
        businessContextParam.appendChild(createID(builder, "urn:fdc:peppol.eu:2017:poacc:billing:01:1.0"));
        exchangedDocumentContext.appendChild(businessContextParam);
        Element guidelineContextParam = builder.createElement(NS_RAM, "GuidelineSpecifiedDocumentContextParameter");
        guidelineContextParam.appendChild(createID(builder, "urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0"));
        exchangedDocumentContext.appendChild(guidelineContextParam);
        return exchangedDocumentContext;
    }

    private Element createExchangedDocument(XmlDocumentBuilder builder, List<InvoiceNote> invoiceNotes) {
        Element exchangedDocument = builder.createElement(NS_RSM, "ExchangedDocument");
        exchangedDocument.appendChild(createID(builder, invoice.getDocumentId()));
        exchangedDocument.appendChild(createTypeCode(builder, invoice.getDocumentTypeCode()));
        Element issueDateTime = builder.createElement(NS_RAM, "IssueDateTime");
        exchangedDocument.appendChild(issueDateTime);
        issueDateTime.appendChild(createDateTimeString(builder, invoice.getIssueDate(), NS_UDT));
        for (InvoiceNote note : invoiceNotes) {
            if (XRechnungUtils.isNotNullOrBlank(note.getNote())) {
                exchangedDocument.appendChild(createIncludedNote(builder, note));
            }
        }
        return exchangedDocument;
    }

    private static Element createTradeLineItem(Item lineItem, XmlDocumentBuilder builder) {
        Element tradeLineItem = builder.createElement(NS_RAM, "IncludedSupplyChainTradeLineItem");

        Element associatedLineItem = builder.createElement(NS_RAM, "AssociatedDocumentLineDocument");
        tradeLineItem.appendChild(associatedLineItem);
        Element lineId = builder.createElement(NS_RAM, "LineID");
        if (lineItem.getId() != null) {
            lineId.setTextContent(lineItem.getId().toString());
        }
        associatedLineItem.appendChild(lineId);

        Element specifiedProduct = builder.createElement(NS_RAM, "SpecifiedTradeProduct");
        tradeLineItem.appendChild(specifiedProduct);
        if (XRechnungUtils.isNotNullOrBlank(lineItem.getSellerAssignedId())) {
            Element sellerAssignedId = builder.createElement(NS_RAM, "SellerAssignedID");
            sellerAssignedId.setTextContent(lineItem.getSellerAssignedId());
            specifiedProduct.appendChild(sellerAssignedId);
        }
        specifiedProduct.appendChild(createName(builder, lineItem.getName()));
        if (XRechnungUtils.isNotNullOrBlank(lineItem.getDescription())) {
            specifiedProduct.appendChild(createDescription(builder, lineItem.getDescription()));
        }
        for (ItemAttribute attribute : lineItem.getItemAttributes()) {
            specifiedProduct.appendChild(createProductCharacteristic(builder, attribute));
        }

        Element specifiedAgreement = builder.createElement(NS_RAM, "SpecifiedLineTradeAgreement");
        tradeLineItem.appendChild(specifiedAgreement);
        Element netProductTradePrice = builder.createElement(NS_RAM, "NetPriceProductTradePrice");
        specifiedAgreement.appendChild(netProductTradePrice);
        Element chargeAmount = builder.createElement(NS_RAM, "ChargeAmount");
        if (lineItem.getUnitPrice() != null) {
            chargeAmount.setTextContent(lineItem.getUnitPrice().toString());
        }
        netProductTradePrice.appendChild(chargeAmount);
        Element basisQuantity = builder.createElement(NS_RAM, "BasisQuantity");
        basisQuantity.setAttribute("unitCode", lineItem.getUnitCode());
        basisQuantity.setTextContent("1");
        netProductTradePrice.appendChild(basisQuantity);

        Element specifiedDelivery = builder.createElement(NS_RAM, "SpecifiedLineTradeDelivery");
        tradeLineItem.appendChild(specifiedDelivery);
        Element billedQuantity = builder.createElement(NS_RAM, "BilledQuantity");
        billedQuantity.setAttribute("unitCode", lineItem.getUnitCode());
        if (lineItem.getQuantity() != null) {
            billedQuantity.setTextContent(lineItem.getQuantity().toString());
        }
        specifiedDelivery.appendChild(billedQuantity);

        Element specifiedSettlement = builder.createElement(NS_RAM, "SpecifiedLineTradeSettlement");
        tradeLineItem.appendChild(specifiedSettlement);
        specifiedSettlement.appendChild(createTax(builder, lineItem.getVat()));
        Element taxSummation = builder.createElement(NS_RAM, "SpecifiedTradeSettlementLineMonetarySummation");
        specifiedSettlement.appendChild(taxSummation);
        Element taxAmount = builder.createElement(NS_RAM, "LineTotalAmount"); // BT-131
        if (lineItem.getItemTotalNetAmount() != null) {
            taxAmount.setTextContent(lineItem.getItemTotalNetAmount().toString());
        }
        taxSummation.appendChild(taxAmount);
        return tradeLineItem;
    }

    private Element createTradeHeader(XmlDocumentBuilder builder) {
        Element tradeHeader = builder.createElement(NS_RAM, "ApplicableHeaderTradeAgreement");

        Element buyerRef = builder.createElement(NS_RAM, "BuyerReference");
        if (XRechnungUtils.isNotNullOrBlank(invoice.getLeitwegId())) {
            buyerRef.setTextContent(invoice.getLeitwegId());
        } else {
            // > Wenn keine Referenz vorgegeben wurde:
            // >  – „N/A“ (Not Applicable)
            // >  – Einen internen Vermerk, z. B. „Standard Order“ oder „Privatkunde“
            // >  – Eine allgemeine Kennung wie „Keine Referenz vorhanden“
            // Source: https://leitweg-id.de/
            // Use "N/A" as it does not require translation for different locales
            buyerRef.setTextContent("N/A");
        }
        tradeHeader.appendChild(buyerRef);

        SellerOrBuyer sellerInfo = invoice.getSeller();
        if (sellerInfo != null) {
            Element seller = builder.createElement(NS_RAM, "SellerTradeParty");
            tradeHeader.appendChild(seller);
            seller.appendChild(createName(builder, sellerInfo.getName()));
            seller.appendChild(createTradeContact(builder, sellerInfo.getContact()));
            seller.appendChild(createAddress(builder, sellerInfo.getAddress()));
            seller.appendChild(createElectronicAddressEmailElement(builder, sellerInfo.getElectronicAddress()));
            if (XRechnungUtils.isNotNullOrBlank(sellerInfo.getVatId())) {
                seller.appendChild(createTaxRegistration(builder, "VA", sellerInfo.getVatId()));
            }
        }

        SellerOrBuyer buyerInfo = invoice.getBuyer();
        if (buyerInfo != null) {
            Element buyer = builder.createElement(NS_RAM, "BuyerTradeParty");
            tradeHeader.appendChild(buyer);
            buyer.appendChild(createName(builder, buyerInfo.getName()));
            buyer.appendChild(createAddress(builder, buyerInfo.getAddress()));
            buyer.appendChild(createElectronicAddressEmailElement(builder, buyerInfo.getElectronicAddress()));
            if (XRechnungUtils.isNotNullOrBlank(buyerInfo.getVatId())) {
                buyer.appendChild(createTaxRegistration(builder, "VA", buyerInfo.getVatId()));
            }
        }

        Element sellerRefDoc = builder.createElement(NS_RAM, "SellerOrderReferencedDocument");
        tradeHeader.appendChild(sellerRefDoc);
        Element issuerId = builder.createElement(NS_RAM, "IssuerAssignedID");
        issuerId.setTextContent(invoice.getSalesOrderReference());
        sellerRefDoc.appendChild(issuerId);

        return tradeHeader;
    }

    private Element createTradeDelivery(XmlDocumentBuilder builder) {
        Element tradeDelivery = builder.createElement(NS_RAM, "ApplicableHeaderTradeDelivery");
        DeliveryInformation deliveryInfo = invoice.getDeliveryInfo();
        if (deliveryInfo == null) {
            return tradeDelivery;
        }
        Element shipment = builder.createElement(NS_RAM, "ShipToTradeParty");
        tradeDelivery.appendChild(shipment);
        shipment.appendChild(createName(builder, deliveryInfo.getName()));
        shipment.appendChild(createAddress(builder, deliveryInfo.getDeliveryAddress()));

        if (deliveryInfo.getActualDeliveryDate() != null) { // (BT-72)
            Element occurrenceDateTime = builder.createElement(NS_RAM, "OccurrenceDateTime");
            occurrenceDateTime.appendChild(createDateTimeString(builder, deliveryInfo.getActualDeliveryDate(), NS_UDT));
            Element deliveryEvent = builder.createElement(NS_RAM, "ActualDeliverySupplyChainEvent");
            deliveryEvent.appendChild(occurrenceDateTime);
            tradeDelivery.appendChild(deliveryEvent);
        }

        return tradeDelivery;
    }

    private Element createTradeSettlement(XmlDocumentBuilder builder) {
        Element tradeSettlement = builder.createElement(NS_RAM, "ApplicableHeaderTradeSettlement");
        PaymentInstructions paymentInstructions = invoice.getPaymentInstructions();
        if (paymentInstructions != null && paymentInstructions.getDirectDebit() != null) {
            Element creditorRefId = builder.createElement(NS_RAM, "CreditorReferenceID");
            creditorRefId.setTextContent(paymentInstructions.getDirectDebit().getCreditorId());
            tradeSettlement.appendChild(creditorRefId);
        }
        if (paymentInstructions != null && XRechnungUtils.isNotNullOrBlank(paymentInstructions.getRemittanceInfo())) {
            Element paymentRef = builder.createElement(NS_RAM, "PaymentReference");
            paymentRef.setTextContent(paymentInstructions.getRemittanceInfo());
            tradeSettlement.appendChild(paymentRef);
        }
        Element currency = builder.createElement(NS_RAM, "InvoiceCurrencyCode");
        currency.setTextContent(invoice.getCurrency());
        tradeSettlement.appendChild(currency);
        tradeSettlement.appendChild(createPaymentMeans(builder, paymentInstructions));
        for (Vat totalVat : invoice.getVatTotals()) {
            tradeSettlement.appendChild(createTax(builder, totalVat));
        }
        for (Allowance allowance : invoice.getAllowances()) {
            tradeSettlement.appendChild(createAllowance(builder, allowance));
        }
        for (Charge charge : invoice.getCharges()) {
            tradeSettlement.appendChild(createCharge(builder, charge));
        }
        if (paymentInstructions != null && XRechnungUtils.isNotNullOrBlank(paymentInstructions.getPaymentTerms())) {
            Element paymentTerms = builder.createElement(NS_RAM, "SpecifiedTradePaymentTerms");
            tradeSettlement.appendChild(paymentTerms);
            paymentTerms.appendChild(createDescription(builder, paymentInstructions.getPaymentTerms()));
            if (paymentInstructions.getDirectDebit() != null) {
                Element directDebitMandate = builder.createElement(NS_RAM, "DirectDebitMandateID");
                directDebitMandate.setTextContent(paymentInstructions.getDirectDebit().getMandateReference());
                paymentTerms.appendChild(directDebitMandate);
            }
        }
        Element sum = builder.createElement(NS_RAM, "SpecifiedTradeSettlementHeaderMonetarySummation");
        tradeSettlement.appendChild(sum);
        Element lineTotal = builder.createElement(NS_RAM, "LineTotalAmount"); // BT-106
        if (invoice.getLineTotalAmount() != null) {
            lineTotal.setTextContent(invoice.getLineTotalAmount().toString());
        }
        sum.appendChild(lineTotal);

        if (invoice.getChargeTotalAmount() != null) {
            Element chargeTotal = builder.createElement(NS_RAM, "ChargeTotalAmount"); // BT-108
            if (invoice.getChargeTotalAmount() != null) {
                chargeTotal.setTextContent(invoice.getChargeTotalAmount().toString());
            }
            sum.appendChild(chargeTotal);
        }

        if (invoice.getAllowanceTotalAmount() != null) {
            Element allowanceTotal = builder.createElement(NS_RAM, "AllowanceTotalAmount");
            if (invoice.getAllowanceTotalAmount() != null) {
                allowanceTotal.setTextContent(invoice.getAllowanceTotalAmount().toString());
            }
            sum.appendChild(allowanceTotal);
        }

        Element taxBasis = builder.createElement(NS_RAM, "TaxBasisTotalAmount"); // BT-109
        if (invoice.getTaxBasisTotalAmount() != null) {
            taxBasis.setTextContent(invoice.getTaxBasisTotalAmount().toString());
        }
        sum.appendChild(taxBasis);
        if (invoice.getTaxTotalAmount() != null) {
            Element taxTotal = builder.createElement(NS_RAM, "TaxTotalAmount"); // BT-110
            taxTotal.setAttribute("currencyID", invoice.getCurrency());
            taxTotal.setTextContent(invoice.getTaxTotalAmount().toString());
            sum.appendChild(taxTotal);
        }
        Element grandTotal = builder.createElement(NS_RAM, "GrandTotalAmount"); // BT-112
        if (invoice.getGrandTotalAmount() != null) {
            grandTotal.setTextContent(invoice.getGrandTotalAmount().toString());
        }
        sum.appendChild(grandTotal);
        Element duePayable = builder.createElement(NS_RAM, "DuePayableAmount");
        if (invoice.getDuePayableAmount() != null) {
            duePayable.setTextContent(invoice.getDuePayableAmount().toString());
        }
        sum.appendChild(duePayable);

        for (PrecedingInvoiceReference reference : invoice.getPrecedingInvoiceReferences()) {
            tradeSettlement.appendChild(createPrecedingInvoiceReference(builder, reference));
        }

        return tradeSettlement;
    }

    /**
     * BG-3
     */
    private Element createPrecedingInvoiceReference(XmlDocumentBuilder builder, PrecedingInvoiceReference reference) {
        Element element = builder.createElement(NS_RAM, "InvoiceReferencedDocument");
        Element issuerAssignedId = builder.createElement(NS_RAM, "IssuerAssignedID"); // BT-25, mandatory
        issuerAssignedId.setTextContent(reference.getPrecedingInvoiceReference());
        element.appendChild(issuerAssignedId);
        if (reference.getPrecedingInvoiceIssueDate() != null) { // BT-26, optional
            Element issueDateTime = builder.createElement(NS_RAM, "FormattedIssueDateTime");
            issueDateTime.appendChild(createDateTimeString(builder, invoice.getIssueDate(), NS_QDT));
            element.appendChild(issueDateTime);
        }
        return element;
    }

    private static Element createPaymentMeans(XmlDocumentBuilder builder, PaymentInstructions paymentInstructions) {
        Element paymentMeans = builder.createElement(NS_RAM, "SpecifiedTradeSettlementPaymentMeans");
        if (paymentInstructions != null) {
            paymentMeans.appendChild(createTypeCode(builder, paymentInstructions.getMeansType()));
            if (XRechnungUtils.isNotNullOrBlank(paymentInstructions.getMeansText())) {
                Element paymentInfo = builder.createElement(NS_RAM, "Information");
                paymentInfo.setTextContent(paymentInstructions.getMeansText());
                paymentMeans.appendChild(paymentInfo);
            }
            for (CreditTransfer transfer : paymentInstructions.getCreditTransfers()) {
                Element payeeAccount = builder.createElement(NS_RAM, "PayeePartyCreditorFinancialAccount");
                paymentMeans.appendChild(payeeAccount);
                payeeAccount.appendChild(createIban(builder, transfer.getIban()));
                if (XRechnungUtils.isNotNullOrBlank(transfer.getAccountName())) {
                    Element accountName = builder.createElement(NS_RAM, "AccountName");
                    accountName.setTextContent(transfer.getAccountName());
                    payeeAccount.appendChild(accountName);
                }
                if (XRechnungUtils.isNotNullOrBlank(transfer.getBic())) {
                    Element payeeBank = builder.createElement(NS_RAM, "PayeeSpecifiedCreditorFinancialInstitution");
                    paymentMeans.appendChild(payeeBank);
                    Element bic = builder.createElement(NS_RAM, "BICID");
                    bic.setTextContent(transfer.getBic());
                    payeeBank.appendChild(bic);
                }
            }
            if (paymentInstructions.getDirectDebit() != null) {
                Element debtorAccount = builder.createElement(NS_RAM, "PayerPartyDebtorFinancialAccount");
                debtorAccount.appendChild(createIban(builder, paymentInstructions.getDirectDebit().getDebitedAccountIban()));
                paymentMeans.appendChild(debtorAccount);
            }
            PaymentCardInformation cardInformation = paymentInstructions.getPaymentCardInformation();
            if (cardInformation != null) {
                Element card = builder.createElement(NS_RAM, "ApplicableTradeSettlementFinancialCard");
                card.appendChild(createID(builder, cardInformation.getAccountNumber()));
                Element cardHolderName = builder.createElement(NS_RAM, "CardholderName");
                cardHolderName.setTextContent(cardInformation.getCardHolderName());
                card.appendChild(cardHolderName);
                paymentMeans.appendChild(card);
            }
        }
        return paymentMeans;
    }

    private Element createAllowance(XmlDocumentBuilder builder, Allowance allowance) {
        Element element = builder.createElement(NS_RAM, "SpecifiedTradeAllowanceCharge");

        element.appendChild(createChargeIndicator(builder, false));

        Element amount = builder.createElement(NS_RAM, "ActualAmount");
        if (allowance.getNetAmount() != null) {
            amount.setTextContent(allowance.getNetAmount().toString());
        }
        element.appendChild(amount);

        Element reason = builder.createElement(NS_RAM, "Reason");
        reason.setTextContent(allowance.getReason());
        element.appendChild(reason);

        Element tax = builder.createElement(NS_RAM, "CategoryTradeTax");
        element.appendChild(tax);
        tax.appendChild(createTypeCode(builder, VAT_TYPE_CODE));
        if (allowance.getVatCategory() != null) {
            tax.appendChild(createCategoryCode(builder, allowance.getVatCategory().getCategoryCode()));
        }
        if (allowance.getVatRate() != null) {
            tax.appendChild(createRate(builder, allowance.getVatRate().toString()));
        }

        return element;
    }

    private Element createCharge(XmlDocumentBuilder builder, Charge charge) {
        Element element = builder.createElement(NS_RAM, "SpecifiedTradeAllowanceCharge");

        element.appendChild(createChargeIndicator(builder, true));

        Element amount = builder.createElement(NS_RAM, "ActualAmount"); // BT-99
        if (charge.getNetAmount() != null) {
            amount.setTextContent(charge.getNetAmount().toString());
        }
        element.appendChild(amount);

        Element reason = builder.createElement(NS_RAM, "Reason");
        reason.setTextContent(charge.getReason());
        element.appendChild(reason);

        Element tax = builder.createElement(NS_RAM, "CategoryTradeTax");
        element.appendChild(tax);
        tax.appendChild(createTypeCode(builder, VAT_TYPE_CODE));
        if (charge.getVatCategory() != null) {
            tax.appendChild(createCategoryCode(builder, charge.getVatCategory().getCategoryCode()));
        }
        if (charge.getVatRate() != null) {
            tax.appendChild(createRate(builder, charge.getVatRate().toString()));
        }
        return element;
    }

    private static Element createID(XmlDocumentBuilder builder, String id) {
        Element element = builder.createElement(NS_RAM, "ID");
        element.setTextContent(id);
        return element;
    }

    private static Element createTypeCode(XmlDocumentBuilder builder, String code) {
        Element element = builder.createElement(NS_RAM, "TypeCode");
        element.setTextContent(code);
        return element;
    }

    private static Element createName(XmlDocumentBuilder builder, String name) {
        Element element = builder.createElement(NS_RAM, "Name");
        element.setTextContent(name);
        return element;
    }

    private static Element createDescription(XmlDocumentBuilder builder, String description) {
        Element element = builder.createElement(NS_RAM, "Description");
        element.setTextContent(description);
        return element;
    }

    private static Element createDateTimeString(XmlDocumentBuilder builder, OffsetDateTime time, String namespace) {
        Element dateTimeString = builder.createElement(namespace, "DateTimeString");
        dateTimeString.setAttribute("format", "102");
        if (time != null) {
            dateTimeString.setTextContent(time.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        }
        return dateTimeString;
    }

    private static Element createTaxRegistration(XmlDocumentBuilder builder, String scheme, String id) {
        Element element = builder.createElement(NS_RAM, "SpecifiedTaxRegistration");
        Element idElement = createID(builder, id);
        idElement.setAttribute("schemeID", scheme);
        element.appendChild(idElement);
        return element;
    }

    private static Element createAddress(XmlDocumentBuilder builder, PostalAddress address) {
        Element tradeAddress = builder.createElement(NS_RAM, "PostalTradeAddress");
        if (address != null) {
            Element postCode = builder.createElement(NS_RAM, "PostcodeCode");
            postCode.setTextContent(address.getZipCode());
            tradeAddress.appendChild(postCode);
            Element addressLine = builder.createElement(NS_RAM, "LineOne");
            addressLine.setTextContent(address.getAddressLineOne());
            tradeAddress.appendChild(addressLine);
            Element city = builder.createElement(NS_RAM, "CityName");
            city.setTextContent(address.getCity());
            tradeAddress.appendChild(city);
            Element country = builder.createElement(NS_RAM, "CountryID");
            country.setTextContent(address.getCountryIsoCode());
            tradeAddress.appendChild(country);
        }
        return tradeAddress;
    }

    private static Element createTax(XmlDocumentBuilder builder, Vat vat) {
        Element tradeTax = builder.createElement(NS_RAM, "ApplicableTradeTax");
        if (vat != null) {
            if (vat.getTaxAmount() != null) {
                Element calculated = builder.createElement(NS_RAM, "CalculatedAmount"); // BT-117
                calculated.setTextContent(vat.getTaxAmount().toString());
                tradeTax.appendChild(calculated);
            }
            tradeTax.appendChild(createTypeCode(builder, VAT_TYPE_CODE));
            if (vat.getVatExemptionReasonText() != null) {
                Element exemptionReasonText = builder.createElement(NS_RAM, "ExemptionReason"); // BT-120
                exemptionReasonText.setTextContent(vat.getVatExemptionReasonText());
                tradeTax.appendChild(exemptionReasonText);
            }
            if (vat.getVatExemptionReasonCode() != null) {
                Element exemptionReasonCode = builder.createElement(NS_RAM, "ExemptionReasonCode"); // BT-121
                exemptionReasonCode.setTextContent(vat.getVatExemptionReasonCode());
                tradeTax.appendChild(exemptionReasonCode);
            }
            if (vat.getTaxableAmount() != null) {
                Element basis = builder.createElement(NS_RAM, "BasisAmount");
                basis.setTextContent(vat.getTaxableAmount().toString());
                tradeTax.appendChild(basis);
            }
            if (vat.getCategory() != null) {
                tradeTax.appendChild(createCategoryCode(builder, vat.getCategory().getCategoryCode()));
            }
            if (vat.getRate() != null) {
                tradeTax.appendChild(createRate(builder, vat.getRate().toString()));
            }
        }
        return tradeTax;
    }

    private static Element createCategoryCode(XmlDocumentBuilder builder, String category) {
        Element element = builder.createElement(NS_RAM, "CategoryCode");
        element.setTextContent(category);
        return element;
    }

    private static Element createRate(XmlDocumentBuilder builder, String rate) {
        Element element = builder.createElement(NS_RAM, "RateApplicablePercent");
        element.setTextContent(rate);
        return element;
    }

    private static Element createTradeContact(XmlDocumentBuilder builder, Contact contact) {
        Element tradeContact = builder.createElement(NS_RAM, "DefinedTradeContact");
        if (contact != null) {
            Element personName = builder.createElement(NS_RAM, "PersonName");
            personName.setTextContent(contact.getName());
            tradeContact.appendChild(personName);
            Element telephone = builder.createElement(NS_RAM, "TelephoneUniversalCommunication");
            tradeContact.appendChild(telephone);
            Element telephoneNumber = builder.createElement(NS_RAM, "CompleteNumber");
            telephoneNumber.setTextContent(contact.getPhone());
            telephone.appendChild(telephoneNumber);
            Element email = builder.createElement(NS_RAM, "EmailURIUniversalCommunication");
            email.appendChild(createURIID(builder, contact.getEmail(), null));
            tradeContact.appendChild(email);
        }
        return tradeContact;
    }

    private static Element createElectronicAddressEmailElement(XmlDocumentBuilder builder, String email) {
        var communication = builder.createElement(NS_RAM, "URIUniversalCommunication");
        var uriID = createURIID(builder, email, "EM");
        communication.appendChild(uriID);
        return communication;
    }

    private static Element createURIID(XmlDocumentBuilder builder, String id, String scheme) {
        var uriID = builder.createElement(NS_RAM, "URIID");
        if (scheme != null) {
            uriID.setAttribute("schemeID", scheme);
        }
        uriID.setTextContent(id);
        return uriID;
    }

    private static Element createIban(XmlDocumentBuilder builder, String iban) {
        Element element = builder.createElement(NS_RAM, "IBANID");
        element.setTextContent(iban);
        return element;
    }

    private static Element createChargeIndicator(XmlDocumentBuilder builder, boolean isCharge) {
        Element chargeIndicator = builder.createElement(NS_RAM, "ChargeIndicator");
        Element indicator = builder.createElement(NS_UDT, "Indicator");
        indicator.setTextContent(isCharge ? "true" : "false");
        chargeIndicator.appendChild(indicator);
        return chargeIndicator;
    }

    private static Element createProductCharacteristic(XmlDocumentBuilder builder, ItemAttribute attribute) {
        Element element = builder.createElement(NS_RAM, "ApplicableProductCharacteristic");

        Element description = builder.createElement(NS_RAM, "Description");
        description.setTextContent(attribute.getName());
        element.appendChild(description);

        Element value = builder.createElement(NS_RAM, "Value");
        value.setTextContent(attribute.getValue());
        element.appendChild(value);

        return element;
    }

    private static Element createIncludedNote(XmlDocumentBuilder builder, InvoiceNote note) {
        Element includedNote = builder.createElement(NS_RAM, "IncludedNote");
        Element content = builder.createElement(NS_RAM, "Content");
        content.setTextContent(note.getNote());
        includedNote.appendChild(content);
        return includedNote;
    }

    private static byte[] toXml(DOMSource source) throws TransformerException {
        var output = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(output);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
        return output.toByteArray();
    }
}
