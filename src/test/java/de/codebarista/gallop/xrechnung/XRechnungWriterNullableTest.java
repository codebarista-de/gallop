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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that the writer never blows up on partially-filled invoices.
 * Each case leaves one model object entirely unset and asserts only that XML comes out.
 * What that XML looks like for realistic input is {@link XRechnungWriterScenariosTest}'s concern.
 */
public class XRechnungWriterNullableTest {
    private static final InvoiceProfile PROFILE = InvoiceProfile.XRECHNUNG;

    @ParameterizedTest(name = "{0}")
    @MethodSource("de.codebarista.gallop.TestHelper#invoiceProfiles")
    public void doesNotFailWithEmptyInvoice(InvoiceProfile profile) {
        assertThat(XRechnungWriter.generateXML(Invoice.create(), profile)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentInstructions() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentCreditTransfer() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()
                        .creditTransfers(List.of(CreditTransfer.create()))), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentCardInformation() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()
                        .paymentCardInformation(PaymentCardInformation.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyDirectDebit() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()
                        .directDebit(DirectDebit.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptySeller() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .seller(SellerOrBuyer.create()), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPostalAddress() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .seller(SellerOrBuyer.create()
                        .address(PostalAddress.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyContact() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .seller(SellerOrBuyer.create()
                        .contact(Contact.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyBuyer() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .buyer(SellerOrBuyer.create()), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyDeliveryInformation() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .deliveryInfo(DeliveryInformation.create()), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyDeliveryInformationPostAddress() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .deliveryInfo(DeliveryInformation.create()
                        .deliveryAddress(PostalAddress.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyItem() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .items(List.of(Item.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyItemVat() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .items(List.of(Item.create()
                        .vat(Vat.create()))), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyVatTotal() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .vatTotals(List.of(Vat.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPrecedingInvoiceReference() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .precedingInvoiceReferences(List.of(PrecedingInvoiceReference.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyInvoiceNote() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .invoiceNotes(List.of(InvoiceNote.create().note(null))), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyAllowance() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .allowances(List.of(Allowance.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyCharge() {
        assertThat(XRechnungWriter.generateXML(Invoice.create()
                .charges(List.of(Charge.create())), PROFILE)).isNotEmpty();
    }
}
