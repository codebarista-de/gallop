package de.codebarista.gallop;

import java.util.Objects;

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
     * EN16931 conformance level of the CII syntax, as used by both ZUGFeRD 2.x and
     * Factur-X 1.0.7x (where the level is called "COMFORT").
     * <p>
     * FeRD/AWV and FNFE-MPE aligned the two specifications at this level, so they share the same
     * guideline URN and neither requires a business-process URN: at this level the XML Gallop
     * writes is the same document for both formats. {@link #ZUGFERD_EN16931} and
     * {@link #FACTURX_EN16931} are aliases of this profile.
     * <p>
     * Note that ZUGFeRD and Factur-X are hybrid formats combining a PDF/A-3 document with embedded XML;
     * Gallop only produces the XML part. The two differ in how that XML is embedded.
     */
    public static final EInvoiceProfile EN16931_CII = new EInvoiceProfile(
            "EN16931_CII",
            "urn:cen.eu:en16931:2017",
            null);

    /**
     * ZUGFeRD 2.x, EN16931 conformance level.
     * <p>
     * An alias of {@link #EN16931_CII}: at this conformance level ZUGFeRD and Factur-X produce the same XML.
     */
    public static final EInvoiceProfile ZUGFERD_EN16931 = EN16931_CII;

    /**
     * Factur-X 1.0.7x, EN16931 ("COMFORT") conformance level.
     * <p>
     * An alias of {@link #EN16931_CII}: at this conformance level Factur-X and ZUGFeRD produce the same XML.
     */
    public static final EInvoiceProfile FACTURX_EN16931 = EN16931_CII;

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
        this.name = Objects.requireNonNull(name);
        this.guidelineUrn = Objects.requireNonNull(guidelineUrn);
        this.businessProcessUrn = businessProcessUrn;
    }

    /**
     * @return the name of this profile, e.g. {@code "EN16931_CII"}, never {@code null}
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

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof EInvoiceProfile that)) return false;

        return name.equals(that.name)
                && guidelineUrn.equals(that.guidelineUrn)
                && Objects.equals(businessProcessUrn, that.businessProcessUrn);
    }

    @Override
    public final int hashCode() {
        int result = name.hashCode();
        result = 31 * result + guidelineUrn.hashCode();
        result = 31 * result + Objects.hashCode(businessProcessUrn);
        return result;
    }
}
