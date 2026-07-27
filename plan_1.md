# Add ZUGFeRD (DE) and Factur-X (FR) support via a Profile parameter

## Context

Gallop currently only produces XRechnung XML. `XRechnungWriter` builds a CII
(Cross Industry Invoice) DOM tree and hardcodes two identifiers into the
`ExchangedDocumentContext`:

- `BusinessProcessSpecifiedDocumentContextParameter` → always
  `urn:fdc:peppol.eu:2017:poacc:billing:01:1.0` (Peppol BIS Billing)
- `GuidelineSpecifiedDocumentContextParameter` → always
  `urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0` (XRechnung 3.0)

ZUGFeRD (Germany) and Factur-X (France) are the *same* CII syntax and the
same EN16931 semantic data model that Gallop already implements — they only
differ in which guideline URN is declared (and they don't require the Peppol
business-process URN at all). Everything else — the `Invoice`/`Item`/`Vat`/...
model, the DOM-building code, unit codes, VAT handling — is identical. That
makes this the cheapest possible way to add EU-format coverage: parameterize
those two context identifiers instead of hardcoding them.

Italy (FatturaPA) and UBL-based formats (Peppol BIS Billing, used by most
other EU countries) are explicitly out of scope for this plan — they require
a different element vocabulary/writer and are a separate, larger effort.

The API must stay additive: Gallop is published on Maven Central (currently
2.2.0) and `XRechnungWriter.generateXRechnungXML(Invoice)` is public API used
by consumers today. Existing behavior must not change for callers who don't
opt into a new profile.

## Approach

### 1. Introduce a `Profile` type

New class `src/main/java/de/codebarista/gallop/xrechnung/Profile.java`, an
enum (or small final class if per-profile business-process URNs ever need to
differ) with three constants:

- `XRECHNUNG` — guideline `urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0`,
  business process `urn:fdc:peppol.eu:2017:poacc:billing:01:1.0` (current behavior, unchanged)
- `ZUGFERD_EN16931` — guideline `urn:cen.eu:en16931:2017`, no business process element
- `FACTURX_EN16931` — guideline `urn:cen.eu:en16931:2017`, no business process element
  (Factur-X 1.0.7x aligns its EN16931/"COMFORT" profile with ZUGFeRD's; same URN)

Before implementing, double-check these exact URN strings against the
current ZUGFeRD 2.x and Factur-X 1.0.7x specs (KOSIT/FNFE-MPE docs) — cite
the source in a code comment the way the existing XRechnung writer does
(`XRechnung-v3.0.2-Syntax-Binding-Extension-UBL.pdf` reference at
`XRechnungWriter.java:37`).

### 2. Parameterize `XRechnungWriter`

In `src/main/java/de/codebarista/gallop/xrechnung/XRechnungWriter.java`:

- Add a `Profile` field, set via a new constructor `XRechnungWriter(Invoice invoice, Profile profile)`.
- Keep the existing `XRechnungWriter(Invoice invoice)` constructor, now delegating to `this(invoice, Profile.XRECHNUNG)` — behavior-preserving.
- Keep `generateXRechnungXML(Invoice)` (line 70) exactly as-is (defaults to `Profile.XRECHNUNG`).
- Add a new overload `generateXML(Invoice invoice, Profile profile)` for the new use case.
- Update `createExchangedDocumentContext` (lines 113-122) to:
  - use `profile.getGuidelineUrn()` instead of the hardcoded string
  - only emit `BusinessProcessSpecifiedDocumentContextParameter` if `profile.getBusinessProcessUrn()` is non-null

No changes needed to `XmlDocumentBuilder`, the `model` package, or any of the
`createXxx` element-building methods — they're already profile-agnostic CII
mapping code.

### 3. Tests

- Extend `XRechnungWriterScenariosTest` (or add a new `ProfileWriterTest`) with cases that generate XML for `ZUGFERD_EN16931` and `FACTURX_EN16931` using an existing invoice fixture, asserting the context section contains the right guideline URN and omits the business process element.
- Add a regression test asserting `generateXRechnungXML(invoice)` output is byte-identical to `generateXML(invoice, Profile.XRECHNUNG)` output, to lock in the no-behavior-change guarantee for existing callers.

### 4. Docs

- `README.md`: add a short "Supported formats" note explaining `Profile` and showing the `generateXML(invoice, Profile.ZUGFERD_EN16931)` call; mention that unit codes may differ by convention (already noted in `UnitCode.java:23`) so consumers targeting ZUGFeRD may want `H87`-style codes.
- Changelog entry, e.g. `2.3.0: Add Profile parameter for ZUGFeRD/Factur-X (EN16931) support alongside XRechnung`.

## Verification

- `./gradlew test` — existing scenario/golden-file tests must still pass unchanged (proves XRechnung output is untouched).
- New profile tests pass, confirming correct guideline URN per profile and absence of the business-process element for ZUGFeRD/Factur-X.
- Manually diff a generated ZUGFeRD/Factur-X sample against the KOSIT/FNFE-MPE reference examples' `ExchangedDocumentContext` section to sanity-check the URNs before release.
