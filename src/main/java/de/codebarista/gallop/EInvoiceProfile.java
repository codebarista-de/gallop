package de.codebarista.gallop;

/**
 * Formats of e-invoices.
 * <p>
 * The constants defined here cover the formats Gallop ships with; you can supply profiles Gallop does not define yet.
 */
public class EInvoiceProfile {
    /**
     * XRechnung 3.0.
     */
    public static final EInvoiceProfile XRECHNUNG = new EInvoiceProfile(
            "XRECHNUNG",
            "urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0",
            "urn:fdc:peppol.eu:2017:poacc:billing:01:1.0");

    /**
     * ZUGFeRD 2.x, EN16931 conformance level.
     * <p>
     * Source: ZUGFeRD 2.1.1 specification (FeRD/AWV), part 1, section on
     * {@code GuidelineSpecifiedDocumentContextParameter}. ZUGFeRD does not require a
     * business-process URN.
     * <p>
     * Note that ZUGFeRD is a hybrid format combining a PDF/A-3 document with embedded XML;
     * Gallop only produces the XML part.
     */
    public static final EInvoiceProfile ZUGFERD_EN16931 = new EInvoiceProfile(
            "ZUGFERD_EN16931",
            "urn:cen.eu:en16931:2017",
            null);

    /**
     * Factur-X 1.0.7x, EN16931 ("COMFORT") conformance level.
     * <p>
     * Source: Factur-X 1.07 specification (FNFE-MPE). Factur-X aligned its EN16931/COMFORT profile
     * with ZUGFeRD 2.x, so both use the same guideline URN. Factur-X does not require a
     * business-process URN.
     * <p>
     * Note that Factur-X is a hybrid format combining a PDF/A-3 document with embedded XML;
     * Gallop only produces the XML part.
     */
    public static final EInvoiceProfile FACTURX_EN16931 = new EInvoiceProfile(
            "FACTURX_EN16931",
            "urn:cen.eu:en16931:2017",
            null);

    private final String name;
    private final String guidelineUrn;
    private final String businessProcessUrn;

    /**
     * Defines a profile. Use the constants of this class for the formats Gallop already covers.
     *
     * @param name               the name of the profile, used for {@link #toString()}
     * @param guidelineUrn       the URN written as {@code GuidelineSpecifiedDocumentContextParameter},
     *                           identifying the specification the invoice claims to follow
     * @param businessProcessUrn the URN written as {@code BusinessProcessSpecifiedDocumentContextParameter},
     *                           or {@code null} if the format does not require one
     */
    public EInvoiceProfile(String name, String guidelineUrn, String businessProcessUrn) {
        this.name = name;
        this.guidelineUrn = guidelineUrn;
        this.businessProcessUrn = businessProcessUrn;
    }

    /**
     * @return the name of this profile, e.g. {@code "ZUGFERD_EN16931"}, never {@code null}
     */
    public String getName() {
        return name;
    }

    /**
     * @return the URN for the {@code GuidelineSpecifiedDocumentContextParameter}, never {@code null}
     */
    public String getGuidelineUrn() {
        return guidelineUrn;
    }

    /**
     * @return the URN for the {@code BusinessProcessSpecifiedDocumentContextParameter},
     * or {@code null} if this profile does not declare one
     */
    public String getBusinessProcessUrn() {
        return businessProcessUrn;
    }

    @Override
    public String toString() {
        return name;
    }
}
