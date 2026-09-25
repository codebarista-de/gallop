package de.codebarista.gallop;

import de.codebarista.gallop.cii.CIIXMLEInvoiceWriter;
import de.codebarista.gallop.model.Invoice;

import java.util.Objects;

/**
 * Entry point for writing e-invoices from an {@link Invoice}.
 * Contains convenience methods for writing XML for formats defined in {@link EInvoiceFormat}.
 */
public final class EInvoiceWriter {
    private EInvoiceWriter() {
    }

    /**
     * Converts an invoice to a Cross Industry Invoice (CII) XML document for the given {@link EInvoiceFormat}.
     *
     * @param invoice        the {@link Invoice} to serialize to XML, must not be {@code null}
     * @param eInvoiceFormat the format determining which document context identifiers are written,
     *                       must not be {@code null}
     * @return binary CII XML document
     * @throws EInvoiceWriterException if the creation of the XML failed
     */
    public static byte[] generateCIIXML(Invoice invoice, EInvoiceFormat eInvoiceFormat) {
        Objects.requireNonNull(invoice, "Invoice must not be null");
        Objects.requireNonNull(eInvoiceFormat, "Format must not be null");
        var xmlWriter = new CIIXMLEInvoiceWriter(invoice, eInvoiceFormat);
        try {
            return xmlWriter.getXML();
        } catch (Exception e) {
            throw new EInvoiceWriterException("E-invoice creation failed", e);
        }
    }

    /**
     * Converts an invoice to a Cross Industry Invoice (CII) XRechnung 3.0 XML.
     *
     * @param invoice the {@link Invoice} to serialize to XML, must not be {@code null}
     * @return binary XRechnung XML document
     * @throws EInvoiceWriterException if the creation of the XRechnung failed
     * @see EInvoiceFormat#XRECHNUNG
     */
    public static byte[] generateXRechnungCIIXML(Invoice invoice) {
        return generateCIIXML(invoice, EInvoiceFormat.XRECHNUNG);
    }

    /**
     * Converts an invoice to a Cross Industry Invoice (CII) Factur-X XML at the EN16931 ("COMFORT")
     * conformance level.
     * <p>
     * This only generates the Factur-X XML and <b>not</b> the hybrid PDF document.
     * The XML is identical to the one of {@link #generateZugferdXML(Invoice)}, see
     * {@link EInvoiceFormat#EN16931_CORE}.
     *
     * @param invoice the {@link Invoice} to serialize to XML, must not be {@code null}
     * @return binary Factur-X XML document
     * @throws EInvoiceWriterException if the creation of the Factur-X failed
     * @see EInvoiceFormat#FACTURX_EN16931
     */
    public static byte[] generateFacturXXML(Invoice invoice) {
        return generateCIIXML(invoice, EInvoiceFormat.FACTURX_EN16931);
    }

    /**
     * Converts an invoice to a Cross Industry Invoice (CII) ZUGFeRD XML at the EN16931 conformance level.
     * <p>
     * This only generates the ZUGFeRD XML and <b>not</b> the hybrid PDF document.
     * The XML is identical to the one of {@link #generateFacturXXML(Invoice)}, see
     * {@link EInvoiceFormat#EN16931_CORE}.
     *
     * @param invoice the {@link Invoice} to serialize to XML, must not be {@code null}
     * @return binary ZUGFeRD XML document
     * @throws EInvoiceWriterException if the creation of the ZUGFeRD failed
     * @see EInvoiceFormat#ZUGFERD_EN16931
     */
    public static byte[] generateZugferdXML(Invoice invoice) {
        return generateCIIXML(invoice, EInvoiceFormat.ZUGFERD_EN16931);
    }
}
