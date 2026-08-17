package de.codebarista.gallop.xrechnung;

import de.codebarista.gallop.TestHelper;
import de.codebarista.gallop.xrechnung.model.Invoice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Verifies that {@link InvoiceProfile} specifics are handled correctly.
 */
public class InvoiceProfileTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("de.codebarista.gallop.TestHelper#invoiceProfiles")
    public void declaresAGuidelineUrn(InvoiceProfile profile) {
        Assertions.assertFalse(profile.getGuidelineUrn().isBlank());
    }

    @Test
    public void onlyXRechnungDeclaresABusinessProcessUrn() {
        Assertions.assertEquals("urn:fdc:peppol.eu:2017:poacc:billing:01:1.0",
                InvoiceProfile.XRECHNUNG.getBusinessProcessUrn());
        Assertions.assertNull(InvoiceProfile.ZUGFERD_EN16931.getBusinessProcessUrn());
        Assertions.assertNull(InvoiceProfile.FACTURX_EN16931.getBusinessProcessUrn());
    }

    /**
     * The only thing a profile may change is the {@code ExchangedDocumentContext}. Everything else is
     * profile-agnostic CII mapping, and must stay identical across profiles for the same invoice.
     */
    @ParameterizedTest(name = "{0} / {1}")
    @MethodSource("de.codebarista.gallop.TestHelper#invoiceProfilesAndScenarios")
    public void profilesDifferOnlyInDocumentContext(InvoiceProfile profile, String scenario) {
        Invoice invoice = new TestHelper("invoice").deserialize(scenario + "/invoice.json", Invoice.class);

        Assertions.assertEquals(
                stripDocumentContext(XRechnungWriter.generateXML(invoice, InvoiceProfile.XRECHNUNG)),
                stripDocumentContext(XRechnungWriter.generateXML(invoice, profile)));
    }

    private static String stripDocumentContext(byte[] xml) {
        return new String(xml).replaceAll("(?s)<rsm:ExchangedDocumentContext>.*?</rsm:ExchangedDocumentContext>", "");
    }

    /**
     * ZUGFeRD and Factur-X align their EN16931 profiles on the same guideline URN, so for identical input
     * their XML is identical too, including the document context. This pins that down: if a future spec
     * revision makes them diverge, the fixtures and this assertion have to be updated together,
     * deliberately.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("de.codebarista.gallop.TestHelper#invoiceScenarios")
    public void zugferdAndFacturXProduceIdenticalXml(String scenario) {
        Invoice invoice = new TestHelper("invoice").deserialize(scenario + "/invoice.json", Invoice.class);

        Assertions.assertEquals(
                new String(XRechnungWriter.generateXML(invoice, InvoiceProfile.ZUGFERD_EN16931)),
                new String(XRechnungWriter.generateXML(invoice, InvoiceProfile.FACTURX_EN16931)));
    }

    /**
     * Locks in the guarantee that the deprecated implicit-profile entry points keep producing output
     * identical to explicitly requesting {@link InvoiceProfile#XRECHNUNG}. Existing callers must not see
     * any behaviour change from the introduction of {@link InvoiceProfile}.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("de.codebarista.gallop.TestHelper#invoiceScenarios")
    @SuppressWarnings("deprecation")
    public void deprecatedEntryPointsMatchExplicitXRechnungProfile(String scenario)
            throws ParserConfigurationException, TransformerException {
        Invoice invoice = new TestHelper("invoice").deserialize(scenario + "/invoice.json", Invoice.class);

        String explicit = new String(XRechnungWriter.generateXML(invoice, InvoiceProfile.XRECHNUNG));

        Assertions.assertEquals(explicit, new String(XRechnungWriter.generateXRechnungXML(invoice)));
        Assertions.assertEquals(explicit, new String(new XRechnungWriter(invoice).getXML()));
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
