package de.codebarista.gallop.xrechnung;

import de.codebarista.gallop.TestHelper;
import de.codebarista.gallop.xrechnung.model.Invoice;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.xmlunit.assertj.XmlAssert;
import org.xmlunit.builder.Input;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.InputStream;

/**
 * "Golden-file" test: for every invoice scenario and every {@link InvoiceProfile}, the generated XML must
 * match the checked-in expected output.
 *
 * <p>Scenarios and profiles are registered once in {@link TestHelper}.</p>
 */
public class InvoiceScenarioXmlTest {

    @ParameterizedTest(name = "{0} / {1}")
    @MethodSource("de.codebarista.gallop.TestHelper#invoiceProfilesAndScenarios")
    public void writesExpectedXml(InvoiceProfile profile, String scenario)
            throws ParserConfigurationException, TransformerException {
        String expectedFileName = TestHelper.expectedXmlFileName(profile);
        TestHelper testHelper = new TestHelper("invoice");
        var invoice = testHelper.deserialize(scenario + "/invoice.json", Invoice.class);

        var xml = new XRechnungWriter(invoice, profile).getXML();

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
