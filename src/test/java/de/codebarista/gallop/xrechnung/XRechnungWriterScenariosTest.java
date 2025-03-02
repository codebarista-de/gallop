package de.codebarista.gallop.xrechnung;

import de.codebarista.gallop.TestHelper;
import de.codebarista.gallop.xrechnung.model.Invoice;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.xmlunit.builder.Input;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.InputStream;

import static org.xmlunit.assertj.XmlAssert.assertThat;

/**
 * Test class for verifying the correctness of XRechnung XML generation.
 *
 * <p>This test uses parameterized test cases to validate the conversion of various
 * invoice scenarios into XRechnung-compliant XML. Each test case loads an invoice
 * from a JSON file, generates the corresponding XML, and compares it against an
 * expected XML reference.</p>
 *
 * <p>Scenarios tested include:</p>
 * <ul>
 *     <li>Invoices with different tax rates and allowances</li>
 *     <li>Invoices with various payment methods (e.g., PayPal, cash on delivery)</li>
 *     <li>Orders with discounts, surcharges, and shipping costs</li>
 *     <li>Cases with different billing and shipping addresses</li>
 *     <li>Invoices with tax-exempt products</li>
 * </ul>
 *
 * <p>If a test fails, the generated XML is printed to the console for debugging.</p>
 */
public class XRechnungWriterScenariosTest {
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
            "order_with_tax_free_product"
    })
    public void writeXRechnung(String testFile) throws ParserConfigurationException, TransformerException {
        TestHelper testHelper = new TestHelper("invoice");

        var invoice = testHelper.deserialize(testFile + "/invoice.json", Invoice.class);
        var xRechnungWriter = new XRechnungWriter(invoice);
        var xRechnung = xRechnungWriter.getXML();

        try {
            InputStream expected = testHelper.loadResource(testFile + "/xrechnung.xml");
            assertThat(expected).isNotNull();
            assertThat(Input.fromByteArray(xRechnung))
                    .and(Input.fromStream(expected))
                    .ignoreWhitespace()
                    .ignoreComments()
                    .areIdentical();
        } catch (Exception e) {
            System.out.println(new String(xRechnung));
        }
    }
}
