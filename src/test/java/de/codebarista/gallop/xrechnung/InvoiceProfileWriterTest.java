package de.codebarista.gallop.xrechnung;

import de.codebarista.gallop.TestHelper;
import de.codebarista.gallop.xrechnung.model.Invoice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.xmlunit.assertj.XmlAssert;
import org.xmlunit.builder.Input;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Verifies that {@link InvoiceProfile} selects the correct {@code ExchangedDocumentContext} identifiers,
 * and that the deprecated implicit-profile entry points remain behaviour-identical to explicitly passing
 * {@link InvoiceProfile#XRECHNUNG}.
 *
 * <p>Every scenario under {@code src/test/resources/invoice} is checked against every profile, so each
 * scenario directory must carry one expected XML file per profile (see {@link #EXPECTED_XML_FILE_NAMES}).
 * {@link XRechnungWriterScenariosTest} covers the XRechnung profile; this class covers the rest.</p>
 */
public class InvoiceProfileWriterTest {
    /**
     * All profiles Gallop can write, paired with the name of the expected-output file that every
     * scenario directory holds for them.
     * <p>
     * {@link InvoiceProfile} is a class rather than an enum (so profiles can be added without breaking
     * exhaustive switches downstream), which means there is no {@code values()} to enumerate. Adding a
     * profile therefore means adding it here — and adding the matching fixtures — so it does not
     * silently go untested.
     */
    private static final Map<InvoiceProfile, String> EXPECTED_XML_FILE_NAMES = new LinkedHashMap<>();

    static {
        EXPECTED_XML_FILE_NAMES.put(InvoiceProfile.XRECHNUNG, "xrechnung.xml");
        EXPECTED_XML_FILE_NAMES.put(InvoiceProfile.ZUGFERD_EN16931, "zugferd.xml");
        EXPECTED_XML_FILE_NAMES.put(InvoiceProfile.FACTURX_EN16931, "facturx.xml");
    }

    private static Stream<InvoiceProfile> profiles() {
        return EXPECTED_XML_FILE_NAMES.keySet().stream();
    }

    /**
     * Cartesian product of every profile and every invoice scenario.
     */
    private static Stream<Arguments> profilesAndScenarios() {
        return EXPECTED_XML_FILE_NAMES.keySet().stream().flatMap(profile ->
                TestHelper.INVOICE_SCENARIOS.stream().map(scenario -> Arguments.of(profile, scenario)));
    }

    @ParameterizedTest(name = "{0} / {1}")
    @MethodSource("profilesAndScenarios")
    public void writesExpectedXmlForProfile(InvoiceProfile profile, String scenario)
            throws ParserConfigurationException, TransformerException {
        String expectedFileName = EXPECTED_XML_FILE_NAMES.get(profile);
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

    /**
     * The only thing a profile may change is the {@code ExchangedDocumentContext}. Everything else is
     * profile-agnostic CII mapping, and must stay byte-identical across profiles for the same invoice.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("de.codebarista.gallop.TestHelper#invoiceScenarios")
    public void profilesDifferOnlyInDocumentContext(String scenario) {
        TestHelper testHelper = new TestHelper("invoice");
        var invoice = testHelper.deserialize(scenario + "/invoice.json", Invoice.class);

        String xRechnung = stripDocumentContext(XRechnungWriter.generateXML(invoice, InvoiceProfile.XRECHNUNG));
        String zugferd = stripDocumentContext(XRechnungWriter.generateXML(invoice, InvoiceProfile.ZUGFERD_EN16931));
        String facturX = stripDocumentContext(XRechnungWriter.generateXML(invoice, InvoiceProfile.FACTURX_EN16931));

        Assertions.assertEquals(xRechnung, zugferd);
        Assertions.assertEquals(xRechnung, facturX);
    }

    private static String stripDocumentContext(byte[] xml) {
        return new String(xml).replaceAll("(?s)<rsm:ExchangedDocumentContext>.*?</rsm:ExchangedDocumentContext>", "");
    }

    /**
     * ZUGFeRD and Factur-X align their EN16931 profiles on the same guideline URN, so for identical input
     * their XML is identical too. This pins that down: if a future spec revision makes them diverge, the
     * fixtures and this assertion have to be updated together, deliberately.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("de.codebarista.gallop.TestHelper#invoiceScenarios")
    public void zugferdAndFacturXProduceIdenticalXml(String scenario) {
        TestHelper testHelper = new TestHelper("invoice");
        var invoice = testHelper.deserialize(scenario + "/invoice.json", Invoice.class);

        Assertions.assertEquals(
                new String(XRechnungWriter.generateXML(invoice, InvoiceProfile.ZUGFERD_EN16931)),
                new String(XRechnungWriter.generateXML(invoice, InvoiceProfile.FACTURX_EN16931)));
    }

    /**
     * Locks in the guarantee that the deprecated {@link XRechnungWriter#generateXRechnungXML(Invoice)}
     * keeps producing byte-identical output to explicitly requesting {@link InvoiceProfile#XRECHNUNG} —
     * existing callers must not see any behavior change from introducing {@link InvoiceProfile}.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("de.codebarista.gallop.TestHelper#invoiceScenarios")
    @SuppressWarnings("deprecation")
    public void deprecatedEntryPointsMatchExplicitXRechnungProfile(String scenario)
            throws ParserConfigurationException, TransformerException {
        TestHelper testHelper = new TestHelper("invoice");
        var invoice = testHelper.deserialize(scenario + "/invoice.json", Invoice.class);

        String explicit = new String(XRechnungWriter.generateXML(invoice, InvoiceProfile.XRECHNUNG));

        Assertions.assertEquals(explicit, new String(XRechnungWriter.generateXRechnungXML(invoice)));
        Assertions.assertEquals(explicit, new String(new XRechnungWriter(invoice).getXML()));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void guidelineUrnIsNeverBlank(InvoiceProfile profile) {
        Assertions.assertFalse(profile.getGuidelineUrn().isBlank());
    }

    @Test
    public void onlyXRechnungDeclaresABusinessProcessUrn() {
        Assertions.assertEquals("urn:fdc:peppol.eu:2017:poacc:billing:01:1.0",
                InvoiceProfile.XRECHNUNG.getBusinessProcessUrn());
        Assertions.assertNull(InvoiceProfile.ZUGFERD_EN16931.getBusinessProcessUrn());
        Assertions.assertNull(InvoiceProfile.FACTURX_EN16931.getBusinessProcessUrn());
    }

    @Test
    public void rejectsNullArguments() {
        Invoice invoice = Invoice.create();

        Assertions.assertThrows(NullPointerException.class,
                () -> new XRechnungWriter(null, InvoiceProfile.XRECHNUNG));
        Assertions.assertThrows(NullPointerException.class,
                () -> new XRechnungWriter(invoice, null));
        Assertions.assertThrows(NullPointerException.class,
                () -> XRechnungWriter.generateXML(null, InvoiceProfile.XRECHNUNG));
        Assertions.assertThrows(NullPointerException.class,
                () -> XRechnungWriter.generateXML(invoice, null));
    }
}
