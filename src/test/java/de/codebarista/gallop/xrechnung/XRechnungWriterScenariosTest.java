package de.codebarista.gallop.xrechnung;

import de.codebarista.gallop.TestHelper;
import de.codebarista.gallop.xrechnung.model.Invoice;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.xmlunit.builder.Input;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.InputStream;

import static org.xmlunit.assertj.XmlAssert.assertThat;

/**
 * Test class for verifying the correctness of XRechnung XML generation.
 *
 * <p>This test uses parameterized test cases to validate the conversion of various
 * invoice scenarios into XRechnung-compliant XML. Each test case loads an invoice
 * from a JSON file, generates the corresponding XML, and compares it against an
 * expected XML reference.</p>
 *
 * <p>Scenarios tested include:</p>
 * <ul>
 *     <li>Invoices with different tax rates and allowances</li>
 *     <li>Invoices with various payment methods (e.g., PayPal, cash on delivery)</li>
 *     <li>Orders with discounts, surcharges, and shipping costs</li>
 *     <li>Cases with different billing and shipping addresses</li>
 *     <li>Invoices with tax-exempt products</li>
 * </ul>
 *
 * <p>If a test fails, the generated XML is printed to the console for debugging.</p>
 */
public class XRechnungWriterScenariosTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("de.codebarista.gallop.TestHelper#invoiceScenarios")
    public void writeXRechnung(String testFile) throws ParserConfigurationException, TransformerException {
        TestHelper testHelper = new TestHelper("invoice");

        var invoice = testHelper.deserialize(testFile + "/invoice.json", Invoice.class);
        var xRechnungWriter = new XRechnungWriter(invoice, InvoiceProfile.XRECHNUNG);
        var xRechnung = xRechnungWriter.getXML();

        InputStream expected = testHelper.loadResource(testFile + "/xrechnung.xml");
        assertThat(Input.fromByteArray(xRechnung))
                .withFailMessage("Generated XRechnung for '%s' does not match the expected XML:%n%s",
                        testFile, new String(xRechnung))
                .and(Input.fromStream(expected))
                .ignoreWhitespace()
                .ignoreComments()
                .areIdentical();
    }
}
