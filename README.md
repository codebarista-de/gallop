# Gallop 🐎 E-Invoice Library

[![Tests](https://github.com/codebarista-de/gallop/actions/workflows/test.yml/badge.svg)](https://github.com/codebarista-de/gallop/actions/workflows/test.yml)

Gallop is a Java library for creating electronic invoices (E-Invoices) in the Cross Industry Invoice (CII) syntax:
[XRechnung](https://xeinkauf.de/dokumente/), ZUGFeRD and Factur-X.

    Specification: Standard XRechnung
    Version: XRechnung 3.0.2
    Release Date: June 20, 2024

It does not yet implement the whole specification. Contributions are welcome!

## Why another e-invoice library?

Despite the existence of several mature Java libraries for electronic invoice creation, none provided what we needed: a
permissive license combined with complete control over the output. Gallop was built to fill this gap.

### Gallop does not impose

Gallop does not manipulate your data. It writes the exact values you provide into the XML, with no
calculations or transformations (aside from necessary XML escaping).

This preservation of your original values eliminates rounding discrepancies between source data and the final invoice.
This is ideal, when creating an e-invoice that must match an existing PDF invoice.

### Gallop does not judge

Gallop does not validate the e-invoices it generates. It will happily accept any input and do its best to create a valid
e-invoice, but will not notice or complain when the result does not meet all the rules specified in the
choosen e-invoice format.

There are other tools like the [KOSIT Validator](https://github.com/itplr-kosit/validator)
which verify that the generated XML is a valid X-Rechnung.

## Supported formats

`CIIXMLEInvoiceWriter` writes XRechnung 3.0 as well as the EN16931 ("COMFORT") conformance level of ZUGFeRD (Germany)
and Factur-X (France). Which one you get is decided by the `EInvoiceProfile` you pass:

```java
byte[] xml = CIIXMLEInvoiceWriter.generateXML(invoice, EInvoiceProfile.EN16931_CII);
```

XRechnung, ZUGFeRD and Factur-X all share the same Cross Industry Invoice (CII) syntax and the same EN16931 semantic
data model, so the same `Invoice` object works for all three. Only the document context identifiers differ, and Gallop
takes care of that based on the `EInvoiceProfile`.

ZUGFeRD and Factur-X aligned their specifications at the EN16931 level, so a single profile,
`EInvoiceProfile.EN16931_CII`, covers both. The XML Gallop writes is the same document.
The constants `ZUGFERD_EN16931` and `FACTURX_EN16931` are aliases of `EN16931_CII`.

Note that ZUGFeRD and Factur-X are hybrid formats combining a PDF/A-3 document with embedded XML; Gallop only produces
the XML part, embedding it into a PDF/A-3 document is up to you.

Also note that unit codes conventionally differ by format: XRechnung examples use `XPP` for "piece",
while ZUGFeRD examples use `H87` (see `UnitCode.java`). Pick the unit code your target format/validator expects.

## Usage

Add Gallop to your project via [Maven Central](https://central.sonatype.com/artifact/de.codebarista/gallop):

**Gradle (Groovy DSL):**

```groovy
dependencies {
    implementation 'de.codebarista:gallop:3.0.0'
}
```

**Maven:**

```xml

<dependency>
    <groupId>de.codebarista</groupId>
    <artifactId>gallop</artifactId>
    <version>3.0.0</version>
</dependency>
```

### Code example

You find this code in the `BuildInvoiceTest` class.

```java
public class InvoiceGenerator {

    public String generateInvoice() throws EInvoiceWriterException {
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
                .paidAmount(new BigDecimal("100.00")) // Already paid amount
                .roundingAmount(new BigDecimal("0.02")) // Rounding amount
                .duePayableAmount(new BigDecimal("530.10")) // Amount due for payment

                // Sales order reference
                .salesOrderReference("SO-98765");

        // Generate the XRechnung XML from the invoice
        byte[] xRechnungXML = CIIXMLEInvoiceWriter.generateXML(invoice, EInvoiceProfile.XRECHNUNG);
        return new String(xRechnungXML);
    }
}
```

### Changelog

- 3.0.0: Add ZUGFeRD and Factur-X (both covered by the single shared profile `EN16931_CII`) support alongside
  XRechnung via the new `EInvoiceProfile` parameter of `CIIXMLEInvoiceWriter`. **Breaking:** the packages were
  reorganized: the model classes moved from
  `de.codebarista.gallop.xrechnung.model` to `de.codebarista.gallop.model`, and
  `XRechnungWriterException` became `de.codebarista.gallop.EInvoiceWriterException`;
  `XRechnungUtils` moved to `de.codebarista.gallop.GallopUtils` and
  `XmlDocumentBuilder` to `de.codebarista.gallop.XmlDocumentBuilder`.
  `de.codebarista.gallop.xrechnung.XRechnungWriter` keeps its package and its API, and now delegates to
  `CIIXMLEInvoiceWriter`.
- 2.2.0: Add BT-114 (Rounding amount)
- 2.1.0: Add BT-30/BT-47 (Seller/Buyer legal registration identifier), BT-32 (Seller tax registration identifier), BT-33
  (Seller additional legal information), BT-113 (Paid amount)
  and `NetAmount#getVatCategory`
- 2.0.0: Gallop no longer relies on lombok, introduce fluent api
- 1.0.1: Add action to publish to maven central
- 1.0.0: Initial version
