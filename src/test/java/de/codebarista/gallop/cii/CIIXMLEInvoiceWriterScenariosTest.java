package de.codebarista.gallop.cii;

import de.codebarista.gallop.EInvoiceProfile;
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
 * For every invoice scenario and every {@link EInvoiceProfile}, the generated XML must
 * match the checked-in expected output.
 *
 * <p>Scenarios and profiles are registered once in {@link ScenarioHelper}.</p>
 */
public class CIIXMLEInvoiceWriterScenariosTest {

    @ParameterizedTest(name = "{0} / {1}")
    @MethodSource("de.codebarista.gallop.ScenarioHelper#invoiceProfilesAndScenarios")
    public void writesExpectedXml(EInvoiceProfile profile, String scenario)
            throws ParserConfigurationException, TransformerException {
        String expectedFileName = ScenarioHelper.expectedXmlFileName(profile);
        TestHelper testHelper = new TestHelper("invoice");
        var invoice = testHelper.deserialize(scenario + "/invoice.json", Invoice.class);

        var xml = new CIIXMLEInvoiceWriter(invoice, profile).getXML();

        InputStream expected = testHelper.loadResource(scenario + "/" + expectedFileName);
        XmlAssert.assertThat(Input.fromByteArray(xml))
                .withFailMessage("Generated %s XML for '%s' does not match %s:%n%s",
                        profile, scenario, expectedFileName, new String(xml))
                .and(Input.fromStream(expected))
                .ignoreWhitespace()
                .ignoreComments()
                .areIdentical();
    }
}
