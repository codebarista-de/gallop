package de.codebarista.gallop.xrechnung;

import de.codebarista.gallop.EInvoiceProfile;
import de.codebarista.gallop.EInvoiceWriterException;
import de.codebarista.gallop.cii.CIIXMLEInvoiceWriter;
import de.codebarista.gallop.model.Invoice;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.Objects;

/**
 * Writes a XRechnung XML with the data of an {@linkplain Invoice} object.
 * <p>
 * This is a convenience wrapper around {@link CIIXMLEInvoiceWriter} with {@link EInvoiceProfile#XRECHNUNG} preselected.
 */
public class XRechnungWriter {
    private final CIIXMLEInvoiceWriter writer;

    /**
     * Constructs a new {@code XRechnungWriter} with the specified invoice.
     *
     * @param invoice the invoice to be written, must not be {@code null}
     */
    public XRechnungWriter(Invoice invoice) {
        this.writer = new CIIXMLEInvoiceWriter(invoice, EInvoiceProfile.XRECHNUNG);
    }

    /**
     * Convert an invoice to a XRechnung XML
     *
     * @param invoice the Invoice object to serialize to XML, must not be {@code null}
     * @return binary XRechnung XML document
     * @throws EInvoiceWriterException if the creation of the XRechnung failed
     */
    public static byte[] generateXRechnungXML(Invoice invoice) {
        Objects.requireNonNull(invoice, "Invoice must not be null");
        var xmlWriter = new XRechnungWriter(invoice);
        try {
            return xmlWriter.getXML();
        } catch (Exception e) {
            throw new EInvoiceWriterException("XRechnung creation failed", e);
        }
    }

    /**
     * Generates the XRechnung XML representation of the invoice.
     *
     * @return a byte array containing the serialized XML document
     * @throws ParserConfigurationException if an error occurs while creating the XML document
     * @throws TransformerException         if an error occurs during XML transformation
     */
    public byte[] getXML() throws ParserConfigurationException, TransformerException {
        return writer.getXML();
    }
}
