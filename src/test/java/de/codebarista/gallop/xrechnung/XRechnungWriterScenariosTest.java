package de.codebarista.gallop.xrechnung;

import de.codebarista.gallop.TestHelper;
import de.codebarista.gallop.xrechnung.model.Invoice;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.xmlunit.assertj.XmlAssert;
import org.xmlunit.builder.Input;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

/**
 * "Golden-file" test: for every invoice scenario and every {@link InvoiceProfile}, the generated XML must
 * match the checked-in expected output.
 *
 * <p>Scenarios and profiles are registered once in {@link TestHelper}.</p>
 */
public class XRechnungWriterScenariosTest {

    /**
     * Names of the invoice scenario directories under {@code src/test/resources/invoice}.
     * <p>
     * Every scenario directory holds an {@code invoice.json} plus one expected XML per {@link InvoiceProfile}.
     */
    public static final List<String> INVOICE_SCENARIOS = List.of(
            "order_with_allowance",
            "order_with_already_paid_amount",
            "order_with_belgian_tax_rates_and_document_comment",
            "order_with_cash_payment",
            "order_with_credit_and_surcharge",
            "order_with_custom_line_item_type",
            "order_with_customer_vatid",
            "order_with_different_billing_and_shipping_address",
            "order_with_discount_code_and_shipping_costs_with_multiple_taxes",
            "order_with_legal_registration_identifiers",
            "order_with_payment_in_advance",
            "order_with_paypal",
            "order_with_paypal_credit_card",
            "order_with_paypal_direct_debit",
            "order_with_paypal_invoice",
            "order_with_rounding_amount",
            "order_with_shipping_costs_with_multiple_taxes",
            "order_with_tax_free_product",
            "order_without_vatid"
    );

    /**
     * @return the cartesian product of every profile and every scenario, for use as a JUnit
     * {@code @MethodSource}
     */
    public static Stream<Arguments> invoiceProfilesAndScenarios() {
        return TestHelper.invoiceProfiles().flatMap(profile ->
                INVOICE_SCENARIOS.stream().map(scenario -> Arguments.of(profile, scenario)));
    }

    @ParameterizedTest(name = "{0} / {1}")
    @MethodSource("invoiceProfilesAndScenarios")
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
