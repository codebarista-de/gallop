package de.codebarista.gallop.xrechnung;

/**
 * Formats of e-invoices.
 */
public class InvoiceProfile {
    /**
     * XRechnung 3.0.
     * <p>
     * Source: {@code XRechnung-v3.0.2-Syntax-Binding-Extension-UBL.pdf} (KOSIT), see also
     * {@link XRechnungWriter} class javadoc.
     */
    public static final InvoiceProfile XRECHNUNG = new InvoiceProfile(
            "XRECHNUNG",
            "urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0",
            "urn:fdc:peppol.eu:2017:poacc:billing:01:1.0");

    /**
     * ZUGFeRD 2.x, EN16931 conformance level.
     * <p>
     * Source: ZUGFeRD 2.1.1 specification (FeRD/AWV), part 1, section on
     * {@code GuidelineSpecifiedDocumentContextParameter}. ZUGFeRD does not require a
     * business-process URN.
     */
    public static final InvoiceProfile ZUGFERD_EN16931 = new InvoiceProfile(
            "ZUGFERD_EN16931",
            "urn:cen.eu:en16931:2017",
            null);

    /**
     * Factur-X 1.0.7x, EN16931 ("COMFORT") conformance level.
     * <p>
     * Source: Factur-X 1.07 specification (FNFE-MPE). Factur-X aligned its EN16931/COMFORT profile
     * with ZUGFeRD 2.x, so both use the same guideline URN. Factur-X does not require a
     * business-process URN.
     */
    public static final InvoiceProfile FACTURX_EN16931 = new InvoiceProfile(
            "FACTURX_EN16931",
            "urn:cen.eu:en16931:2017",
            null);

    private final String name;
    private final String guidelineUrn;
    private final String businessProcessUrn;

    InvoiceProfile(String name, String guidelineUrn, String businessProcessUrn) {
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
