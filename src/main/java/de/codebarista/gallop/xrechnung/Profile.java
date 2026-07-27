package de.codebarista.gallop.xrechnung;

/**
 * Selects which {@code ExchangedDocumentContext} identifiers {@link XRechnungWriter} writes.
 * <p>
 * XRechnung, ZUGFeRD (Germany) and Factur-X (France) all use the same Cross Industry Invoice (CII)
 * syntax and the same EN16931 semantic data model — they only differ in the guideline and
 * business-process URNs declared in the document context. Everything else {@link XRechnungWriter}
 * produces is identical across profiles.
 * <p>
 * Note that {@code ZUGFERD_EN16931} and {@code FACTURX_EN16931} only cover the EN16931 ("COMFORT")
 * conformance level. Other conformance levels (e.g. ZUGFeRD/Factur-X BASIC, MINIMUM, EXTENDED) use
 * different guideline URNs and are not covered by this enum. Also note that ZUGFeRD and Factur-X are
 * hybrid formats that combine a PDF/A-3 document with an embedded XML; {@link XRechnungWriter} only
 * produces the XML part — embedding it into a PDF/A-3 document is the caller's responsibility.
 */
public enum Profile {
    /**
     * XRechnung 3.0.
     * <p>
     * Source: {@code XRechnung-v3.0.2-Syntax-Binding-Extension-UBL.pdf} (KOSIT), see also
     * {@link XRechnungWriter} class javadoc.
     */
    XRECHNUNG(
            "urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0",
            "urn:fdc:peppol.eu:2017:poacc:billing:01:1.0"),

    /**
     * ZUGFeRD 2.x, EN16931 conformance level.
     * <p>
     * Source: ZUGFeRD 2.1.1 specification (FeRD/AWV), part 1, section on
     * {@code GuidelineSpecifiedDocumentContextParameter}. ZUGFeRD does not require a
     * business-process URN.
     */
    ZUGFERD_EN16931("urn:cen.eu:en16931:2017", null),

    /**
     * Factur-X 1.0.7x, EN16931 ("COMFORT") conformance level.
     * <p>
     * Source: Factur-X 1.07 specification (FNFE-MPE). Factur-X aligned its EN16931/COMFORT profile
     * with ZUGFeRD 2.x, so both use the same guideline URN. Factur-X does not require a
     * business-process URN.
     */
    FACTURX_EN16931("urn:cen.eu:en16931:2017", null);

    private final String guidelineUrn;
    private final String businessProcessUrn;

    Profile(String guidelineUrn, String businessProcessUrn) {
        this.guidelineUrn = guidelineUrn;
        this.businessProcessUrn = businessProcessUrn;
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
}
