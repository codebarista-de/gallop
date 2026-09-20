package de.codebarista.gallop.cii;

import de.codebarista.gallop.EInvoiceProfile;
import de.codebarista.gallop.model.Allowance;
import de.codebarista.gallop.model.Charge;
import de.codebarista.gallop.model.Contact;
import de.codebarista.gallop.model.CreditTransfer;
import de.codebarista.gallop.model.DeliveryInformation;
import de.codebarista.gallop.model.DirectDebit;
import de.codebarista.gallop.model.Invoice;
import de.codebarista.gallop.model.InvoiceNote;
import de.codebarista.gallop.model.Item;
import de.codebarista.gallop.model.PaymentCardInformation;
import de.codebarista.gallop.model.PaymentInstructions;
import de.codebarista.gallop.model.PostalAddress;
import de.codebarista.gallop.model.PrecedingInvoiceReference;
import de.codebarista.gallop.model.SellerOrBuyer;
import de.codebarista.gallop.model.Vat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that the writer never blows up on partially-filled invoices.
 * Each case leaves one model object entirely unset and asserts only that XML comes out.
 * What that XML looks like for realistic input is {@link CIIXMLEInvoiceWriterScenariosTest}'s concern.
 */
public class CIIXMLEInvoiceWriterNullableTest {
    private static final EInvoiceProfile PROFILE = EInvoiceProfile.XRECHNUNG;

    @ParameterizedTest(name = "{0}")
    @MethodSource("de.codebarista.gallop.ScenarioHelper#invoiceProfiles")
    public void doesNotFailWithEmptyInvoice(EInvoiceProfile profile) {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create(), profile)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentInstructions() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentCreditTransfer() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()
                        .creditTransfers(List.of(CreditTransfer.create()))), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentCardInformation() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()
                        .paymentCardInformation(PaymentCardInformation.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyDirectDebit() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()
                        .directDebit(DirectDebit.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptySeller() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .seller(SellerOrBuyer.create()), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPostalAddress() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .seller(SellerOrBuyer.create()
                        .address(PostalAddress.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyContact() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .seller(SellerOrBuyer.create()
                        .contact(Contact.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyBuyer() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .buyer(SellerOrBuyer.create()), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyDeliveryInformation() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .deliveryInfo(DeliveryInformation.create()), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyDeliveryInformationPostAddress() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .deliveryInfo(DeliveryInformation.create()
                        .deliveryAddress(PostalAddress.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyItem() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .items(List.of(Item.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyItemVat() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .items(List.of(Item.create()
                        .vat(Vat.create()))), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyVatTotal() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .vatTotals(List.of(Vat.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPrecedingInvoiceReference() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .precedingInvoiceReferences(List.of(PrecedingInvoiceReference.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyInvoiceNote() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .invoiceNotes(List.of(InvoiceNote.create().note(null))), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyAllowance() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .allowances(List.of(Allowance.create())), PROFILE)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyCharge() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .charges(List.of(Charge.create())), PROFILE)).isNotEmpty();
    }
}
