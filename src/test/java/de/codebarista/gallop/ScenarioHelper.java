package de.codebarista.gallop;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ScenarioHelper {
    /**
     * Names of the invoice scenario directories under {@code src/test/resources/invoice}.
     * <p>
     * Every scenario directory holds an {@code invoice.json} plus one expected XML per {@link EInvoiceFormat}.
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
     * @return every scenario name, for use as a JUnit {@code @MethodSource}
     */
    public static Stream<String> invoiceScenarios() {
        return INVOICE_SCENARIOS.stream();
    }

    /**
     * Every format Gallop can write, paired with the name of the expected-output file that each
     * scenario directory holds for it.
     */
    public static final Map<EInvoiceFormat, String> EXPECTED_XML_FILE_NAMES = Map.of(
            EInvoiceFormat.XRECHNUNG, "xrechnung.xml",
            EInvoiceFormat.EN16931_CORE, "en16931-core.xml"
    );

    /**
     * @return every {@link EInvoiceFormat}, for use as a JUnit {@code @MethodSource}
     */
    public static Stream<EInvoiceFormat> invoiceFormats() {
        return EXPECTED_XML_FILE_NAMES.keySet().stream();
    }

    /**
     * @return the cartesian product of every format and every scenario, for use as a JUnit
     * {@code @MethodSource}
     */
    public static Stream<Arguments> invoiceFormatsAndScenarios() {
        return invoiceFormats().flatMap(format ->
                INVOICE_SCENARIOS.stream().map(scenario -> Arguments.of(format, scenario)));
    }

    /**
     * @return the expected-output file name for {@code format}, e.g. {@code "en16931-core.xml"}
     */
    public static String expectedXmlFileName(EInvoiceFormat format) {
        return EXPECTED_XML_FILE_NAMES.get(format);
    }
}
