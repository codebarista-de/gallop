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
    implementation 'de.codebarista:gallop:2.0.0'
}
```

**Maven:**

```xml

<dependency>
    <groupId>de.codebarista</groupId>
    <artifactId>gallop</artifactId>
    <version>2.0.0</version>
</dependency>
```

### Code example

You find this code in the `BuildInvoiceTest` class.

```java
public class InvoiceGenerator {

    public String generateInvoice() throws XRechnungWriterException {
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

        // Generate the XRechnung XML from the invoice
        byte[] xRechnungXML = XRechnungWriter.generateXRechnungXML(invoice);
        return new String(xRechnungXML);
    }
}
```

### Changelog

- 2.0.0: Gallop no longer relies on lombok, introduce fluent api
- 1.0.1: Add action to publish to maven central
- 1.0.0: Initial version
