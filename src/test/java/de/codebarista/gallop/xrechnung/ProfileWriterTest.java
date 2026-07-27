package de.codebarista.gallop.xrechnung;

import de.codebarista.gallop.TestHelper;
import de.codebarista.gallop.xrechnung.model.Invoice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.xmlunit.builder.Input;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.xmlunit.assertj.XmlAssert.assertThat;

/**
 * Verifies that {@link Profile} selects the correct document context identifiers, and that the
 * existing {@code generateXRechnungXML} entry point remains behavior-identical to
 * {@code generateXML(invoice, Profile.XRECHNUNG)}.
 */
public class ProfileWriterTest {
    private static final String TEST_FILE = "order_with_paypal_direct_debit";

    @Test
    public void zugferdContextOmitsBusinessProcessAndUsesEn16931Guideline() throws ParserConfigurationException, TransformerException {
        assertProfileMatchesFixture(Profile.ZUGFERD_EN16931, "zugferd.xml");
    }

    @Test
    public void facturxContextOmitsBusinessProcessAndUsesEn16931Guideline() throws ParserConfigurationException, TransformerException {
        assertProfileMatchesFixture(Profile.FACTURX_EN16931, "facturx.xml");
    }

    private void assertProfileMatchesFixture(Profile profile, String expectedFileName) throws ParserConfigurationException, TransformerException {
        TestHelper testHelper = new TestHelper("invoice");
        var invoice = testHelper.deserialize(TEST_FILE + "/invoice.json", Invoice.class);
        var xmlWriter = new XRechnungWriter(invoice, profile);
        var xml = xmlWriter.getXML();

        InputStream expected = testHelper.loadResource(TEST_FILE + "/" + expectedFileName);
        assertThat(expected).isNotNull();
        assertThat(Input.fromByteArray(xml))
                .and(Input.fromStream(expected))
                .ignoreWhitespace()
                .ignoreComments()
                .areIdentical();
    }

    /**
     * Locks in the guarantee that {@link XRechnungWriter#generateXRechnungXML(Invoice)} keeps
     * producing byte-identical output to explicitly requesting {@link Profile#XRECHNUNG} — existing
     * callers must not see any behavior change from introducing {@link Profile}.
     */
    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {
            "order_with_allowance",
            "order_with_belgian_tax_rates_and_document_comment",
            "order_with_cash_payment",
            "order_with_credit_and_surcharge",
            "order_with_custom_line_item_type",
            "order_with_customer_vatid",
            "order_with_different_billing_and_shipping_address",
            "order_with_discount_code_and_shipping_costs_with_multiple_taxes",
            "order_with_payment_in_advance",
            "order_with_paypal",
            "order_with_paypal_credit_card",
            "order_with_paypal_direct_debit",
            "order_with_paypal_invoice",
            "order_with_shipping_costs_with_multiple_taxes",
            "order_with_tax_free_product",
            "order_with_already_paid_amount",
            "order_with_rounding_amount",
            "order_without_vatid",
            "order_with_legal_registration_identifiers"
    })
    public void defaultEntryPointMatchesExplicitXRechnungProfile(String testFile) {
        TestHelper testHelper = new TestHelper("invoice");
        var invoice = testHelper.deserialize(testFile + "/invoice.json", Invoice.class);

        var defaultXml = XRechnungWriter.generateXRechnungXML(invoice);
        var explicitXml = XRechnungWriter.generateXML(invoice, Profile.XRECHNUNG);

        assertThat(Input.fromByteArray(defaultXml))
                .and(Input.fromByteArray(explicitXml))
                .areIdentical();
    }

    @ParameterizedTest(name = "{0}")
    @EnumSource(Profile.class)
    public void guidelineUrnIsNeverBlank(Profile profile) {
        assertFalse(profile.getGuidelineUrn().isBlank());
    }
}
