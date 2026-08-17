package de.codebarista.gallop;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.codebarista.gallop.xrechnung.InvoiceProfile;
import de.codebarista.gallop.xrechnung.model.TaxCategory;
import org.junit.jupiter.params.provider.Arguments;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Utility class for assisting with test data loading and deserialization.
 *
 * <p>This class provides methods to load test resources and deserialize JSON files
 * into Java objects using Jackson. It also includes a custom deserializer for
 * handling {@link TaxCategory} values.</p>
 */
public class TestHelper {
    /**
     * Names of the invoice scenario directories under {@code src/test/resources/invoice}.
     * <p>
     * Every scenario directory holds an {@code invoice.json} plus one expected XML per
     * {@link InvoiceProfile} (see {@link #EXPECTED_XML_FILE_NAMES}). Shared by all scenario-driven tests
     * via {@code @MethodSource} so a new scenario only has to be registered once.
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
     * @return the invoice scenario names, for use as a JUnit {@code @MethodSource}
     */
    public static List<String> invoiceScenarios() {
        return INVOICE_SCENARIOS;
    }

    /**
     * Every profile Gallop can write, paired with the name of the expected-output file that each
     * scenario directory holds for it.
     * <p>
     * {@link InvoiceProfile} is a class rather than an enum (so profiles can be added without breaking
     * exhaustive switches downstream), which means there is no {@code values()} to enumerate. Adding a
     * profile therefore means adding it here — and adding the matching fixtures, so it does not
     * silently go untested.
     */
    public static final Map<InvoiceProfile, String> EXPECTED_XML_FILE_NAMES = Map.of(
            InvoiceProfile.XRECHNUNG, "xrechnung.xml",
            InvoiceProfile.ZUGFERD_EN16931, "zugferd.xml",
            InvoiceProfile.FACTURX_EN16931, "facturx.xml"
    );

    /**
     * @return every {@link InvoiceProfile}, for use as a JUnit {@code @MethodSource}
     */
    public static Stream<InvoiceProfile> invoiceProfiles() {
        return EXPECTED_XML_FILE_NAMES.keySet().stream();
    }

    /**
     * @return the cartesian product of every profile and every scenario, for use as a JUnit
     * {@code @MethodSource}
     */
    public static Stream<Arguments> invoiceProfilesAndScenarios() {
        return invoiceProfiles().flatMap(profile ->
                INVOICE_SCENARIOS.stream().map(scenario -> Arguments.of(profile, scenario)));
    }

    /**
     * @return the expected-output file name for {@code profile}, e.g. {@code "zugferd.xml"}
     */
    public static String expectedXmlFileName(InvoiceProfile profile) {
        return EXPECTED_XML_FILE_NAMES.get(profile);
    }

    private final ObjectMapper objectMapper;
    private final String basePath;

    /**
     * Constructs a {@code TestHelper} with a specified base path for resource loading.
     *
     * @param basePath the base directory where test resources are located
     */
    public TestHelper(String basePath) {
        this.basePath = basePath;
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());

        // Register custom deserializer
        // We use Jackson only for tests and thus can't annotate the property with @JsonValue
        SimpleModule module = new SimpleModule();
        module.addDeserializer(TaxCategory.class, new TaxCategoryDeserializer());
        objectMapper.registerModule(module);
    }

    /**
     * Loads a resource as an input stream from the specified path.
     *
     * @param path the relative path to the resource
     * @return an {@code InputStream} of the resource, never {@code null}
     * @throws RuntimeException if the resource does not exist or cannot be loaded. A missing expected-output
     *                          file is a test gap, not a passing test, so it must fail loudly.
     */
    public InputStream loadResource(String path) {
        InputStream inputStream;
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(Path.of(basePath, path).toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load resource: " + path, e);
        }
        if (inputStream == null) {
            throw new RuntimeException("Resource does not exist: " + Path.of(basePath, path));
        }
        return inputStream;
    }

    /**
     * Deserializes a JSON file from the specified path into a Java object.
     *
     * @param path      the relative path to the JSON file
     * @param valueType the class type to deserialize into
     * @param <T>       the generic type parameter
     * @return the deserialized object
     */
    public <T> T deserialize(String path, Class<T> valueType) {
        try {
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream(Path.of(basePath, path).toString());
            return objectMapper.readValue(inputStream, valueType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize JSON from: " + path, e);
        }
    }

    /**
     * Custom Jackson deserializer for {@link TaxCategory}.
     * <p>
     * This deserializer converts a string representation of a tax category
     * into the corresponding {@link TaxCategory} enum instance.
     */
    static class TaxCategoryDeserializer extends JsonDeserializer<TaxCategory> {
        @Override
        public TaxCategory deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String category = p.getText();
            return TaxCategory.fromCategory(category);
        }
    }
}
