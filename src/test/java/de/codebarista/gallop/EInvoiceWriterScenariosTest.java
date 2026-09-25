package de.codebarista.gallop;

import de.codebarista.gallop.model.Invoice;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.xmlunit.assertj.XmlAssert;
import org.xmlunit.builder.Input;

import java.io.InputStream;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * For every invoice scenario, each convenience method of {@link EInvoiceWriter} must produce
 * the checked-in expected output of its format.
 *
 * <p>Scenarios and formats are registered once in {@link ScenarioHelper}.</p>
 */
public class EInvoiceWriterScenariosTest {

    static Stream<Arguments> convenienceMethodsAndScenarios() {
        Stream<Arguments> methods = Stream.of(
                Arguments.of(Named.<Function<Invoice, byte[]>>of("generateXRechnungCIIXML",
                        EInvoiceWriter::generateXRechnungCIIXML), EInvoiceFormat.XRECHNUNG),
                Arguments.of(Named.<Function<Invoice, byte[]>>of("generateZugferdXML",
                        EInvoiceWriter::generateZugferdXML), EInvoiceFormat.ZUGFERD_EN16931),
                Arguments.of(Named.<Function<Invoice, byte[]>>of("generateFacturXXML",
                        EInvoiceWriter::generateFacturXXML), EInvoiceFormat.FACTURX_EN16931));
        return methods.flatMap(method -> ScenarioHelper.INVOICE_SCENARIOS.stream()
                .map(scenario -> Arguments.of(method.get()[0], method.get()[1], scenario)));
    }

    @ParameterizedTest(name = "{0} / {2}")
    @MethodSource("convenienceMethodsAndScenarios")
    public void writesExpectedXml(Function<Invoice, byte[]> generate, EInvoiceFormat format, String scenario) {
        String expectedFileName = ScenarioHelper.expectedXmlFileName(format);
        TestHelper testHelper = new TestHelper("invoice");
        var invoice = testHelper.deserialize(scenario + "/invoice.json", Invoice.class);

        var xml = generate.apply(invoice);

        InputStream expected = testHelper.loadResource(scenario + "/" + expectedFileName);
        XmlAssert.assertThat(Input.fromByteArray(xml))
                .withFailMessage("Generated %s XML for '%s' does not match %s:%n%s",
                        format, scenario, expectedFileName, new String(xml))
                .and(Input.fromStream(expected))
                .ignoreWhitespace()
                .ignoreComments()
                .areIdentical();
    }
}
