package de.codebarista.gallop;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.codebarista.gallop.model.TaxCategory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Utility class for assisting with test data loading and deserialization.
 *
 * <p>This class provides methods to load test resources and deserialize JSON files
 * into Java objects using Jackson. It also includes a custom deserializer for
 * handling {@link TaxCategory} values.</p>
 */
public class TestHelper {
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
