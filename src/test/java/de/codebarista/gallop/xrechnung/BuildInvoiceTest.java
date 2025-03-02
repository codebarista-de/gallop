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
        Invoice invoice = Invoice.builder()
                .documentTypeCode(InvoiceType.COMMERCIAL_INVOICE.getValue()) // Define invoice type
                .documentId("INV-2025-1001") // Unique invoice identifier
                .leitwegId("N/A") // Buyer reference (BT-10)
                .currency("EUR") // Currency used for the invoice

                // Payment details including method and terms
                .paymentInstructions(PaymentInstructions.builder()
                        .meansType(PaymentCode.CASH)
                        .meansText("Cash on delivery")
                        .paymentTerms("The goods remain our property until full payment is received."
                                + "\nDate of service corresponds to invoice date.")
                        .build())

                .issueDate(OffsetDateTime.now()) // Invoice issue date

                // Seller details
                .seller(SellerOrBuyer.builder()
                        .name("TechNova Solutions GmbH")
                        .address(PostalAddress.builder()
                                .addressLineOne("Innovationsstraße 15")
                                .city("Berlin")
                                .zipCode("10115")
                                .countryIsoCode("DE")
                                .build())
                        .vatId("DE298765432") // Seller VAT ID
                        .electronicAddress("billing@technova.com") // Electronic address for invoicing
                        .contact(Contact.builder()
                                .name("Dr. Stefan Wagner")
                                .phone("+49 (0) 30 987654321")
                                .email("stefan.wagner@technova.com")
                                .build())
                        .build())

                // Buyer details
                .buyer(SellerOrBuyer.builder()
                        .name("Greenline Retail AG")
                        .address(PostalAddress.builder()
                                .addressLineOne("Einkaufsstraße 78")
                                .city("Hamburg")
                                .zipCode("20095")
                                .countryIsoCode("DE")
                                .build())
                        .electronicAddress("finance@greenlineretail.com") // Electronic address for buyer
                        .build())

                // Delivery information
                .deliveryInfo(DeliveryInformation.builder()
                        .name("Greenline Retail AG - Warehouse")
                        .deliveryAddress(PostalAddress.builder()
                                .addressLineOne("Lagerstraße 5")
                                .city("Hamburg")
                                .zipCode("21079")
                                .countryIsoCode("DE")
                                .build())
                        .build())

                // Invoice items
                .items(List.of(
                        Item.builder()
                                .id(1L)
                                .name("Ergonomic Office Chair")
                                .sellerAssignedId("CHAIR-ERG-2025") // Seller's internal product ID
                                .quantity(2L) // Quantity purchased
                                .unitPrice(new BigDecimal("199.99")) // Price per unit
                                .itemTotalNetAmount(new BigDecimal("399.98")) // Total price without VAT
                                .unitCode(UnitCode.PIECE) // Unit of measurement
                                .vat(Vat.builder()
                                        .rate(BigDecimal.valueOf(19)) // VAT rate (19%)
                                        .category(TaxCategory.STANDARD_RATE)
                                        .build())
                                .build(),
                        Item.builder()
                                .id(2L)
                                .name("Wireless Mechanical Keyboard")
                                .sellerAssignedId("KEY-MECH-WL")
                                .quantity(1L)
                                .unitPrice(new BigDecimal("129.50"))
                                .itemTotalNetAmount(new BigDecimal("129.50"))
                                .unitCode(UnitCode.PIECE)
                                .vat(Vat.builder()
                                        .rate(BigDecimal.valueOf(19))
                                        .category(TaxCategory.STANDARD_RATE)
                                        .build())
                                .build()
                ))

                // VAT breakdown
                .vatTotals(List.of(
                        Vat.builder()
                                .rate(BigDecimal.valueOf(19))
                                .category(TaxCategory.STANDARD_RATE)
                                .taxableAmount(BigDecimal.valueOf(529.48)) // Taxable amount
                                .taxAmount(BigDecimal.valueOf(100.60)) // VAT amount
                                .build()
                ))

                // Invoice totals
                .lineTotalAmount(new BigDecimal("529.48")) // Total of all line items
                .taxBasisTotalAmount(new BigDecimal("529.48")) // Tax basis total
                .taxTotalAmount(new BigDecimal("100.60")) // Total VAT amount
                .grandTotalAmount(new BigDecimal("630.08")) // Invoice total with VAT
                .duePayableAmount(new BigDecimal("630.08")) // Amount due for payment

                // Sales order reference
                .salesOrderReference("SO-98765")

                .build();

        // Ensure the invoice object is not null
        assertThat(invoice).isNotNull();

        // Generate the XRechnung XML from the invoice
        byte[] xRechnungXML = XRechnungWriter.generateXRechnungXML(invoice);
        String xml = new String(xRechnungXML);

        // Print the generated XML to the console
        System.out.println(xml);
    }
}
