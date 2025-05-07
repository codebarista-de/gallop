# Gallop 🐎 XRechnung Library

Gallop is a Java library for creating electronic invoices (E-Invoices) compliant to
the [XRechnung standard](https://xeinkauf.de/dokumente/).

    Specification: Standard XRechnung
    Version: XRechnung 3.0.2
    Release Date: June 20, 2024

It does not yet implement the whole specification. Contributions are welcome!

## Why another e-invoice library?

Despite the existence of several mature Java libraries for electronic invoice creation,
none provided what we needed: a permissive license combined with complete control over the output.
Gallop was built to fill this gap.

### Gallop does not impose

Gallop does not manipulate your data. It writes the exact values you provide into the XRechnung XML,
with no calculations or transformations (aside from necessary XML escaping).

This preservation of your original values eliminates rounding discrepancies between source data and the final invoice.
This is ideal, when creating an e-invoice that must match an existing PDF invoice.

### Gallop does not judge

Gallop does not validate the e-invoices it generates.
It will happily accept any input and do its best to create a valid e-invoice,
but will not notice or complain when the result does not meet all the rules specified in the XRechnung standard.

There are other tools like the [KOSIT Validator](https://github.com/itplr-kosit/validator)
which verify that the generated XML is a valid X-Rechnung.

## Usage

Add Gallop to your project via [Maven Central](https://central.sonatype.com/artifact/de.codebarista/gallop):

**Gradle (Groovy DSL):**

```groovy
dependencies {
    implementation 'de.codebarista:gallop:1.0.1'
}
```

**Maven:**

```xml

<dependency>
    <groupId>de.codebarista</groupId>
    <artifactId>gallop</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Code example

```java
import de.codebarista.gallop.xrechnung.model.Invoice;
import de.codebarista.gallop.xrechnung.model.Item;
import de.codebarista.gallop.xrechnung.model.InvoiceNote;
import de.codebarista.gallop.XmlDocumentBuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class InvoiceGenerator {

    public byte[] generateInvoice() throws Exception {
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

        byte[] xRechnungXML = XRechnungWriter.generateXRechnungXML(invoice);
    }
}
```

You also find this code in the `BuildInvoiceTest` class.
