package de.codebarista.gallop.xrechnung;

import de.codebarista.gallop.EInvoiceFormat;
import de.codebarista.gallop.ScenarioHelper;
import de.codebarista.gallop.TestHelper;
import de.codebarista.gallop.model.Invoice;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.xmlunit.assertj.XmlAssert;
import org.xmlunit.builder.Input;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.InputStream;

/**
 * For every invoice scenario, the generated XML must match the checked-in expected XRechnung output.
 *
 * <p>Scenarios and formats are registered once in {@link ScenarioHelper}.</p>
 */
public class XRechnungWriterScenariosTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("de.codebarista.gallop.ScenarioHelper#invoiceScenarios")
    public void writesExpectedXml(String scenario)
            throws ParserConfigurationException, TransformerException {
        String expectedFileName = ScenarioHelper.expectedXmlFileName(EInvoiceFormat.XRECHNUNG);
        TestHelper testHelper = new TestHelper("invoice");
        var invoice = testHelper.deserialize(scenario + "/invoice.json", Invoice.class);

        var xml = new XRechnungWriter(invoice).getXML();

        InputStream expected = testHelper.loadResource(scenario + "/" + expectedFileName);
        XmlAssert.assertThat(Input.fromByteArray(xml))
                .withFailMessage("Generated XRechnung XML for '%s' does not match %s:%n%s",
                        scenario, expectedFileName, new String(xml))
                .and(Input.fromStream(expected))
                .ignoreWhitespace()
                .ignoreComments()
                .areIdentical();
    }
}
