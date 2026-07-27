# Add UBL / Peppol BIS Billing support (the rest of the EU)

## Context

`plan_1.md` (ZUGFeRD/Factur-X via a `Profile` parameter) was the cheap win:
those formats reuse Gallop's existing CII syntax and `Invoice` model, so
parameterizing two context URNs was enough. It only covers Germany and
France, though. Most other EU countries that use a structured,
network-distributed e-invoice — Belgium (mandatory B2B Peppol since
2026-01-01), the Netherlands, Denmark, Sweden, Romania (own CIUS, still
EN16931/UBL-based), and the wider Peppol network generally — use **UBL**
(Universal Business Language), not CII. That's a different XML vocabulary
entirely (`cac:`/`cbc:` elements vs. `ram:`/`rsm:`), so it can't be added by
parameterizing the existing `XRechnungWriter` — it needs its own writer.

This is the single highest-leverage next step for broad EU coverage: one
UBL writer targeting **Peppol BIS Billing 3.0** (the pan-European UBL
profile of EN16931) reaches most remaining EU markets through the Peppol
network, since national CIUS's like Romania's are themselves EN16931/UBL
variants layered on the same base syntax.

**Explicitly out of scope** (confirmed with user / carried over from
`plan_1.md`): Italy (FatturaPA — a bespoke non-UBL/non-CII schema). Also out
of scope for this plan: Poland's KSeF and France's Chorus Pro platform
format — both are bespoke/national, not UBL or CII, and would each need
their own dedicated effort. France's B2B mandate is already served by the
Factur-X (CII) support from `plan_1.md`.

## Approach

### 1. New package and writer, model unchanged

Add `src/main/java/de/codebarista/gallop/ubl/UblInvoiceWriter.java` (new
top-level package, sibling to `xrechnung`, since this is no longer
XRechnung-specific — this is the first writer that isn't CII). It:

- Takes the *existing* `de.codebarista.gallop.xrechnung.model.Invoice` — no
  model changes needed, since UBL/Peppol BIS Billing implements the same
  EN16931 semantic model Gallop's model is already tagged with (BT-1, BT-2, …).
  This confirms the earlier architectural bet that keeping the model
  BT/BG-tagged (rather than CII-element-named) paid off.
- Reuses `XmlDocumentBuilder` (`de.codebarista.gallop.xrechnung.XmlDocumentBuilder`)
  for DOM construction — it's already namespace-generic and not CII-specific.
  (Consider moving it to a shared/common package as part of this work, e.g.
  `de.codebarista.gallop.common`, since it'll now be used by two writers.)
- Registers the UBL namespaces: root `urn:oasis:names:specification:ubl:schema:xsd:Invoice-2`,
  plus `cbc:` (`...CommonBasicComponents-2`) and `cac:` (`...CommonAggregateComponents-2`).
- Emits the two document-level identifiers UBL requires in place of CII's
  context params: `cbc:CustomizationID` (the Peppol BIS Billing 3.0
  specification identifier) and `cbc:ProfileID` (the same Peppol business
  process URN already used today, `urn:fdc:peppol.eu:2017:poacc:billing:01:1.0`).
- Public entry point mirrors the existing one for consistency:
  `UblInvoiceWriter.generatePeppolBISBillingXML(Invoice invoice)` returning
  `byte[]`, plus an instance constructor `UblInvoiceWriter(Invoice invoice)` —
  same shape as `XRechnungWriter`.

### 2. Field mapping (BT → UBL element)

Same mapping exercise as the original CII writer, this time against the
[Peppol BIS Billing 3.0 UBL syntax binding](https://docs.peppol.eu/poacc/billing/3.0/syntax/ubl-invoice/tree/).
Representative mappings to build from (verify each against the spec while
implementing, don't trust this table blindly):

| BT | Field | UBL element |
|----|-------|-------------|
| BT-1 | documentId | `cbc:ID` |
| BT-2 | issueDate | `cbc:IssueDate` |
| BT-3 | documentTypeCode | `cbc:InvoiceTypeCode` |
| BT-5 | currency | `cbc:DocumentCurrencyCode` |
| BT-31 | seller.vatId | `cac:AccountingSupplierParty/cac:Party/cac:PartyTaxScheme/cbc:CompanyID` |
| BG-25 | items | `cac:InvoiceLine` (repeatable) |
| BT-106 | lineTotalAmount | `cac:LegalMonetaryTotal/cbc:LineExtensionAmount` |
| BT-109 | taxBasisTotalAmount | `cac:LegalMonetaryTotal/cbc:TaxExclusiveAmount` |
| BT-110 | taxTotalAmount | `cac:TaxTotal/cbc:TaxAmount` |
| BT-112 | grandTotalAmount | `cac:LegalMonetaryTotal/cbc:TaxInclusiveAmount` |
| BT-113 | paidAmount | `cac:LegalMonetaryTotal/cbc:PrepaidAmount` |
| BT-114 | roundingAmount | `cac:LegalMonetaryTotal/cbc:PayableRoundingAmount` |
| BT-115 | duePayableAmount | `cac:LegalMonetaryTotal/cbc:PayableAmount` |

The remaining ~40 fields on `Invoice`/`Item`/`Vat`/`Allowance`/`Charge`/
`PaymentInstructions`/etc. follow the same pattern one-for-one — this is
translation work, not design work, since the semantic model is unchanged.

### 3. Tests

Mirror the existing golden-file pattern:
- Reuse the JSON invoice fixtures under `src/test/resources/invoice/<scenario>/invoice.json`.
- Add a sibling expected-output file per scenario, e.g. `peppol-ubl.xml`, alongside the existing `xrechnung.xml`.
- New `UblInvoiceWriterScenariosTest` (mirrors `XRechnungWriterScenariosTest`) plus a nullable-fields test mirroring `XRechnungWriterNullableTest`.

### 4. Validation reference (docs only, consistent with existing philosophy)

Per the README's existing "Gallop does not judge" stance, keep validation
out of scope for the library itself, but document where to check output:
the [OpenPEPPOL PEPPOL-EN16931-UBL Schematron](https://github.com/OpenPEPPOL/peppol-bis-invoice-3/blob/master/rules/sch/PEPPOL-EN16931-UBL.sch)
and public Peppol validators — same role the KOSIT Validator plays for XRechnung today.

### 5. Follow-ups explicitly deferred (not in this plan)

- **Romania's RO_CIUS**: a national specialization of EN16931/UBL. Once the
  base Peppol BIS Billing writer exists, adding it should be another small
  "quick win" (parameterize `CustomizationID`/a couple of RO-specific rules),
  similar in spirit to the ZUGFeRD/Factur-X profile work — but not free like
  that was, since RO_CIUS has some additional mandatory fields. Worth a
  follow-up plan once this one ships.
- **Poland (KSeF)** and **Chorus Pro-specific formats**: bespoke national
  schemas, not UBL/CII — each would need dedicated research and a dedicated
  writer; not part of this plan.
- **Italy (FatturaPA)**: confirmed out of scope.

## Verification

- `./gradlew test` — new UBL scenario tests pass; existing XRechnung/CII tests untouched (proves no regression/coupling introduced).
- Validate a generated sample against the Peppol BIS Billing Schematron/public validator referenced above.
- Confirm `XRechnungWriter` and `Profile` (from `plan_1.md`) are completely untouched by this change — this is a purely additive new package.
