package de.codebarista.gallop.cii;

import de.codebarista.gallop.EInvoiceFormat;
import de.codebarista.gallop.ScenarioHelper;
import de.codebarista.gallop.TestHelper;
import de.codebarista.gallop.model.Invoice;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * For every invoice scenario and each {@link EInvoiceFormat} it is paired with, the generated XML must
 * match the checked-in expected output.
 *
 * <p>The scenario/format pairings are registered once in {@link ScenarioHelper}.</p>
 */
public class CIIXMLEInvoiceWriterScenariosTest {

    @ParameterizedTest(name = "{0} / {1}")
    @MethodSource("de.codebarista.gallop.ScenarioHelper#invoiceFormatsAndScenarios")
    public void writesExpectedXml(EInvoiceFormat format, String scenario)
            throws ParserConfigurationException, TransformerException {
        TestHelper testHelper = new TestHelper("invoice");
        var invoice = testHelper.deserialize(scenario + "/invoice.json", Invoice.class);

        var xml = new CIIXMLEInvoiceWriter(invoice, format).getXML();

        testHelper.assertXmlMatchesResource(xml, scenario + "/" + ScenarioHelper.expectedXmlFileName(format));
    }
}
