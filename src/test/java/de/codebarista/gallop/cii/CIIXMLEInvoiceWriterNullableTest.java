package de.codebarista.gallop.cii;

import de.codebarista.gallop.EInvoiceFormat;
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
    private static final EInvoiceFormat FORMAT = EInvoiceFormat.XRECHNUNG;

    @ParameterizedTest(name = "{0}")
    @MethodSource("de.codebarista.gallop.ScenarioHelper#invoiceFormats")
    public void doesNotFailWithEmptyInvoice(EInvoiceFormat format) {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create(), format)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentInstructions() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()), FORMAT)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentCreditTransfer() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()
                        .creditTransfers(List.of(CreditTransfer.create()))), FORMAT)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentCardInformation() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()
                        .paymentCardInformation(PaymentCardInformation.create())), FORMAT)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyDirectDebit() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()
                        .directDebit(DirectDebit.create())), FORMAT)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptySeller() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .seller(SellerOrBuyer.create()), FORMAT)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPostalAddress() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .seller(SellerOrBuyer.create()
                        .address(PostalAddress.create())), FORMAT)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyContact() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .seller(SellerOrBuyer.create()
                        .contact(Contact.create())), FORMAT)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyBuyer() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .buyer(SellerOrBuyer.create()), FORMAT)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyDeliveryInformation() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .deliveryInfo(DeliveryInformation.create()), FORMAT)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyDeliveryInformationPostAddress() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .deliveryInfo(DeliveryInformation.create()
                        .deliveryAddress(PostalAddress.create())), FORMAT)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyItem() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .items(List.of(Item.create())), FORMAT)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyItemVat() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .items(List.of(Item.create()
                        .vat(Vat.create()))), FORMAT)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyVatTotal() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .vatTotals(List.of(Vat.create())), FORMAT)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPrecedingInvoiceReference() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .precedingInvoiceReferences(List.of(PrecedingInvoiceReference.create())), FORMAT)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyInvoiceNote() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .invoiceNotes(List.of(InvoiceNote.create().note(null))), FORMAT)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyAllowance() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .allowances(List.of(Allowance.create())), FORMAT)).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyCharge() {
        assertThat(CIIXMLEInvoiceWriter.generateXML(Invoice.create()
                .charges(List.of(Charge.create())), FORMAT)).isNotEmpty();
    }
}
