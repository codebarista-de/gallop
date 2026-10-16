package de.codebarista.gallop;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static de.codebarista.gallop.EInvoiceFormat.EN16931_CORE;
import static de.codebarista.gallop.EInvoiceFormat.XRECHNUNG;

public class ScenarioHelper {
    /**
     * Names of the invoice scenarios (directory names) under {@code src/test/resources/invoice}
     * and the formats they are tested against.
     * <p>
     * The scenario directory holds an {@code invoice.json} plus the expected XML of every listed format.
     */
    public record ScenarioFormats(String scenario, List<EInvoiceFormat> formats) {
        public ScenarioFormats(String scenario, EInvoiceFormat... formats) {
            this(scenario, List.of(formats));
        }
    }

    /**
     * Every scenario with the formats it is tested against.
     */
    public static final List<ScenarioFormats> INVOICE_SCENARIOS_AND_FORMATS = List.of(
            new ScenarioFormats("order_with_allowance", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_already_paid_amount", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_belgian_tax_rates_and_document_comment", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_cash_payment", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_credit_and_surcharge", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_custom_line_item_type", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_customer_vatid", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_different_billing_and_shipping_address", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_discount_code_and_shipping_costs_with_multiple_taxes", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_french_b2c_billing_note_without_buyer_identifiers", EN16931_CORE),
            new ScenarioFormats("order_with_french_mandatory_notes_and_billing_mode", EN16931_CORE),
            new ScenarioFormats("order_with_legal_registration_identifiers", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_leitweg_id_partial_seller_contact_and_buyer_address_scheme", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_payment_in_advance", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_paypal", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_paypal_credit_card", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_paypal_direct_debit", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_paypal_invoice", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_rounding_amount", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_seller_and_buyer_identifiers", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_shipping_costs_with_multiple_taxes", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_with_tax_free_product", XRECHNUNG, EN16931_CORE),
            new ScenarioFormats("order_without_vatid", XRECHNUNG, EN16931_CORE)
    );

    /**
     * Every format Gallop can write, paired with the name of the expected-output file that a
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
     * @return every scenario paired with each of its formats from {@link #INVOICE_SCENARIOS_AND_FORMATS} as
     * {@code (format, scenario)}, for use as a JUnit {@code @MethodSource}
     */
    public static Stream<Arguments> invoiceFormatsAndScenarios() {
        return INVOICE_SCENARIOS_AND_FORMATS.stream().flatMap(scenarioFormats -> scenarioFormats.formats().stream()
                .map(format -> Arguments.of(format, scenarioFormats.scenario())));
    }

    /**
     * @return the expected-output file name for {@code format}, e.g. {@code "en16931-core.xml"}
     */
    public static String expectedXmlFileName(EInvoiceFormat format) {
        return EXPECTED_XML_FILE_NAMES.get(format);
    }
}
