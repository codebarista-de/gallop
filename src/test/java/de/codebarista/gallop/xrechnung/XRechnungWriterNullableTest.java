package de.codebarista.gallop.xrechnung;

import de.codebarista.gallop.xrechnung.model.Allowance;
import de.codebarista.gallop.xrechnung.model.Charge;
import de.codebarista.gallop.xrechnung.model.Contact;
import de.codebarista.gallop.xrechnung.model.CreditTransfer;
import de.codebarista.gallop.xrechnung.model.DeliveryInformation;
import de.codebarista.gallop.xrechnung.model.DirectDebit;
import de.codebarista.gallop.xrechnung.model.Invoice;
import de.codebarista.gallop.xrechnung.model.InvoiceNote;
import de.codebarista.gallop.xrechnung.model.Item;
import de.codebarista.gallop.xrechnung.model.PaymentCardInformation;
import de.codebarista.gallop.xrechnung.model.PaymentInstructions;
import de.codebarista.gallop.xrechnung.model.PostalAddress;
import de.codebarista.gallop.xrechnung.model.PrecedingInvoiceReference;
import de.codebarista.gallop.xrechnung.model.SellerOrBuyer;
import de.codebarista.gallop.xrechnung.model.Vat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that the writer never blows up on partially-filled invoices — it is documented to emit
 * empty or omitted elements rather than to validate its input.
 *
 * <p>Every case runs against every {@link InvoiceProfile}, since the null-handling lives in the shared,
 * profile-agnostic mapping code and must hold for all of them.</p>
 */
public class XRechnungWriterNullableTest {

    private static Stream<InvoiceProfile> profiles() {
        return Stream.of(InvoiceProfile.XRECHNUNG, InvoiceProfile.ZUGFERD_EN16931, InvoiceProfile.FACTURX_EN16931);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyInvoice(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create(), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyPaymentInstructions(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyPaymentCreditTransfer(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()
                        .creditTransfers(List.of(CreditTransfer.create()))), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyPaymentCardInformation(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()
                        .paymentCardInformation(PaymentCardInformation.create())), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyPaymentCardInstructions(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()
                        .directDebit(DirectDebit.create())), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptySeller(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .seller(SellerOrBuyer.create()), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyPostalAddress(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .seller(SellerOrBuyer.create()
                        .address(PostalAddress.create())), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyContact(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .seller(SellerOrBuyer.create()
                        .contact(Contact.create())), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyBuyer(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .buyer(SellerOrBuyer.create()), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyDeliveryInformation(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .deliveryInfo(DeliveryInformation.create()), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyDeliveryInformationPostAddress(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .deliveryInfo(DeliveryInformation.create()
                        .deliveryAddress(PostalAddress.create())), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyItem(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .items(List.of(Item.create())), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyItemVat(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .items(List.of(Item.create()
                        .vat(Vat.create()))), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyVatTotal(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .vatTotals(List.of(Vat.create())), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyPrecedingInvoiceReference(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .precedingInvoiceReferences(List.of(PrecedingInvoiceReference.create())), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyInvoiceNote(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .invoiceNotes(List.of(InvoiceNote.create().note(null))), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyAllowance(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .allowances(List.of(Allowance.create())), profile)).isNotEmpty();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("profiles")
    public void doesNotFailWithEmptyCharge(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .charges(List.of(Charge.create())), profile)).isNotEmpty();
    }

}
