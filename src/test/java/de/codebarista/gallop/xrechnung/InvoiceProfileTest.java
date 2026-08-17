package de.codebarista.gallop.xrechnung;

import de.codebarista.gallop.TestHelper;
import de.codebarista.gallop.xrechnung.model.Invoice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Verifies that {@link InvoiceProfile} specifics are handled correctly.
 */
public class InvoiceProfileTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("de.codebarista.gallop.TestHelper#invoiceProfiles")
    public void declaresAGuidelineUrn(InvoiceProfile profile) {
        assertThat(profile.getGuidelineUrn()).isNotBlank();
    }

    @Test
    public void onlyXRechnungDeclaresABusinessProcessUrn() {
        assertThat("urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")
                .isEqualTo(InvoiceProfile.XRECHNUNG.getBusinessProcessUrn());
        assertThat(InvoiceProfile.ZUGFERD_EN16931.getBusinessProcessUrn()).isNull();
        assertThat(InvoiceProfile.FACTURX_EN16931.getBusinessProcessUrn()).isNull();
    }

    /**
     * The only thing a profile may change is the {@code ExchangedDocumentContext}. Everything else is
     * profile-agnostic CII mapping, and must stay identical across profiles for the same invoice.
     */
    @ParameterizedTest(name = "{0} / {1}")
    @MethodSource("de.codebarista.gallop.TestHelper#invoiceProfilesAndScenarios")
    public void profilesDifferOnlyInDocumentContext(InvoiceProfile profile, String scenario) {
        Invoice invoice = new TestHelper("invoice").deserialize(scenario + "/invoice.json", Invoice.class);

        assertThat(stripDocumentContext(XRechnungWriter.generateXML(invoice, InvoiceProfile.XRECHNUNG)))
                .isEqualTo(stripDocumentContext(XRechnungWriter.generateXML(invoice, profile)));
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

        assertThat(new String(XRechnungWriter.generateXML(invoice, InvoiceProfile.ZUGFERD_EN16931)))
                .isEqualTo(new String(XRechnungWriter.generateXML(invoice, InvoiceProfile.FACTURX_EN16931)));
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

        assertThat(explicit)
                .isEqualTo(new String(XRechnungWriter.generateXRechnungXML(invoice)));
        assertThat(explicit)
                .isEqualTo(new String(new XRechnungWriter(invoice).getXML()));
    }

    @Test
    public void rejectsNullArguments() {
        Invoice invoice = Invoice.create();

        assertThatThrownBy(() -> new XRechnungWriter(null, InvoiceProfile.XRECHNUNG))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> new XRechnungWriter(null, InvoiceProfile.XRECHNUNG))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> new XRechnungWriter(invoice, null))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> XRechnungWriter.generateXML(null, InvoiceProfile.XRECHNUNG))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> XRechnungWriter.generateXML(invoice, null))
                .isInstanceOf(NullPointerException.class);

    }
}
