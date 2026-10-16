package de.codebarista.gallop;

import de.codebarista.gallop.model.Invoice;
import org.junit.jupiter.api.Test;

/**
 * Checks that each convenience method of {@link EInvoiceWriter} writes the XML of its format.
 *
 * <p>Scenario coverage per format lives in
 * {@link de.codebarista.gallop.cii.CIIXMLEInvoiceWriterScenariosTest}.</p>
 */
public class EInvoiceWriterTest {
    private static final String SCENARIO = "order_with_allowance";

    private final TestHelper testHelper = new TestHelper("invoice");
    private final Invoice invoice = testHelper.deserialize(SCENARIO + "/invoice.json", Invoice.class);

    @Test
    public void generateXRechnungCIIXML() {
        testHelper.assertXmlMatchesResource(EInvoiceWriter.generateXRechnungCIIXML(invoice),
                SCENARIO + "/xrechnung.xml");
    }

    @Test
    public void generateZugferdXML() {
        testHelper.assertXmlMatchesResource(EInvoiceWriter.generateZugferdXML(invoice),
                SCENARIO + "/en16931-core.xml");
    }

    @Test
    public void generateFacturXXML() {
        testHelper.assertXmlMatchesResource(EInvoiceWriter.generateFacturXXML(invoice),
                SCENARIO + "/en16931-core.xml");
    }
}
