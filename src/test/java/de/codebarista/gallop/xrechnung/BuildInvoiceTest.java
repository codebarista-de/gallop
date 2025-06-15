package de.codebarista.gallop.xrechnung;

import de.codebarista.gallop.xrechnung.model.Contact;
import de.codebarista.gallop.xrechnung.model.DeliveryInformation;
import de.codebarista.gallop.xrechnung.model.Invoice;
import de.codebarista.gallop.xrechnung.model.InvoiceType;
import de.codebarista.gallop.xrechnung.model.Item;
import de.codebarista.gallop.xrechnung.model.PaymentCode;
import de.codebarista.gallop.xrechnung.model.PaymentInstructions;
import de.codebarista.gallop.xrechnung.model.PostalAddress;
import de.codebarista.gallop.xrechnung.model.SellerOrBuyer;
import de.codebarista.gallop.xrechnung.model.TaxCategory;
import de.codebarista.gallop.xrechnung.model.UnitCode;
import de.codebarista.gallop.xrechnung.model.Vat;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for building an {@link Invoice} object with all necessary details
 * and generating the corresponding XRechnung XML.
 */
public class BuildInvoiceTest {

    @Test
    public void buildInvoice() {
        Invoice invoice = Invoice.create()
                .documentTypeCode(InvoiceType.COMMERCIAL_INVOICE.getValue()) // Define invoice type
                .documentId("INV-2025-1001") // Unique invoice identifier
                .leitwegId("N/A") // Buyer reference (BT-10)
                .currency("EUR") // Currency used for the invoice

                // Payment details including method and terms
                .paymentInstructions(PaymentInstructions.create()
                        .meansType(PaymentCode.CASH)
                        .meansText("Cash on delivery")
                        .paymentTerms("The goods remain our property until full payment is received."
                                + "\nDate of service corresponds to invoice date."))

                .issueDate(OffsetDateTime.now()) // Invoice issue date

                // Seller details
                .seller(SellerOrBuyer.create()
                        .name("TechNova Solutions GmbH")
                        .address(PostalAddress.create()
                                .addressLineOne("Innovationsstraße 15")
                                .city("Berlin")
                                .zipCode("10115")
                                .countryIsoCode("DE"))
                        .vatId("DE298765432") // Seller VAT ID
                        .electronicAddress("billing@technova.com") // Electronic address for invoicing
                        .contact(Contact.create()
                                .name("Dr. Stefan Wagner")
                                .phone("+49 (0) 30 987654321")
                                .email("stefan.wagner@technova.com")))

                // Buyer details
                .buyer(SellerOrBuyer.create()
                        .name("Greenline Retail AG")
                        .address(PostalAddress.create()
                                .addressLineOne("Einkaufsstraße 78")
                                .city("Hamburg")
                                .zipCode("20095")
                                .countryIsoCode("DE"))
                        .electronicAddress("finance@greenlineretail.com")) // Electronic address for buyer

                // Delivery information
                .deliveryInfo(DeliveryInformation.create()
                        .name("Greenline Retail AG - Warehouse")
                        .deliveryAddress(PostalAddress.create()
                                .addressLineOne("Lagerstraße 5")
                                .city("Hamburg")
                                .zipCode("21079")
                                .countryIsoCode("DE")))

                // Invoice items
                .items(List.of(
                        Item.create()
                                .id(1L)
                                .name("Ergonomic Office Chair")
                                .sellerAssignedId("CHAIR-ERG-2025") // Seller's internal product ID
                                .quantity(2L) // Quantity purchased
                                .unitPrice(new BigDecimal("199.99")) // Price per unit
                                .itemTotalNetAmount(new BigDecimal("399.98")) // Total price without VAT
                                .unitCode(UnitCode.PIECE) // Unit of measurement
                                .vat(Vat.create()
                                        .rate(BigDecimal.valueOf(19)) // VAT rate (19%)
                                        .category(TaxCategory.STANDARD_RATE)),
                        Item.create()
                                .id(2L)
                                .name("Wireless Mechanical Keyboard")
                                .sellerAssignedId("KEY-MECH-WL")
                                .quantity(1L)
                                .unitPrice(new BigDecimal("129.50"))
                                .itemTotalNetAmount(new BigDecimal("129.50"))
                                .unitCode(UnitCode.PIECE)
                                .vat(Vat.create()
                                        .rate(BigDecimal.valueOf(19))
                                        .category(TaxCategory.STANDARD_RATE))))

                // VAT breakdown
                .vatTotals(List.of(
                        Vat.create()
                                .rate(BigDecimal.valueOf(19))
                                .category(TaxCategory.STANDARD_RATE)
                                .taxableAmount(BigDecimal.valueOf(529.48)) // Taxable amount
                                .taxAmount(BigDecimal.valueOf(100.60)) // VAT amount
                ))

                // Invoice totals
                .lineTotalAmount(new BigDecimal("529.48")) // Total of all line items
                .taxBasisTotalAmount(new BigDecimal("529.48")) // Tax basis total
                .taxTotalAmount(new BigDecimal("100.60")) // Total VAT amount
                .grandTotalAmount(new BigDecimal("630.08")) // Invoice total with VAT
                .duePayableAmount(new BigDecimal("630.08")) // Amount due for payment

                // Sales order reference
                .salesOrderReference("SO-98765");

        // Ensure the invoice object is not null
        assertThat(invoice).isNotNull();

        // Generate the XRechnung XML from the invoice
        byte[] xRechnungXML = XRechnungWriter.generateXRechnungXML(invoice);
        String xml = new String(xRechnungXML);

        // Print the generated XML to the console
        System.out.println(xml);
    }
}
