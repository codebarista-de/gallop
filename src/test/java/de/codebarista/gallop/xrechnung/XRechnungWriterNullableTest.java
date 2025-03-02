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

import static org.assertj.core.api.Assertions.assertThat;

public class XRechnungWriterNullableTest {

    @Test
    public void doesNotFailWithEmptyInvoice() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder().build())).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentInstructions() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .paymentInstructions(PaymentInstructions.builder().build()).build())
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentCreditTransfer() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .paymentInstructions(PaymentInstructions.builder()
                        .creditTransfer(CreditTransfer.builder().build())
                        .build())
                .build())
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentCardInformation() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .paymentInstructions(PaymentInstructions.builder()
                        .paymentCardInformation(PaymentCardInformation.builder().build())
                        .build())
                .build())
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentCardInstructions() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .paymentInstructions(PaymentInstructions.builder()
                        .directDebit(DirectDebit.builder().build())
                        .build())
                .build())
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptySeller() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .seller(SellerOrBuyer.builder().build())
                .build())
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPostalAddress() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .seller(SellerOrBuyer.builder()
                        .address(PostalAddress.builder().build())
                        .build())
                .build())
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyContact() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .seller(SellerOrBuyer.builder()
                        .contact(Contact.builder().build())
                        .build())
                .build())
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyBuyer() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .buyer(SellerOrBuyer.builder().build())
                .build())
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyDeliveryInformation() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .deliveryInfo(DeliveryInformation.builder().build())
                .build())
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyDeliveryInformationPostAddress() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .deliveryInfo(DeliveryInformation.builder()
                        .deliveryAddress(PostalAddress.builder().build())
                        .build())
                .build())
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyItem() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .item(Item.builder().build())
                .build())
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyItemVat() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .item(Item.builder()
                        .vat(Vat.builder().build())
                        .build())
                .build())
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyVatTotal() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .vatTotal(Vat.builder().build())
                .build())
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPrecedingInvoiceReference() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .precedingInvoiceReference(PrecedingInvoiceReference.builder().build())
                .build())
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyInvoiceNote() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .invoiceNote(InvoiceNote.builder().build())
                .build())
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyAllowance() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .allowance(Allowance.builder().build())
                .build())
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyCharge() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.builder()
                .charge(Charge.builder().build())
                .build())
        ).isNotEmpty();
    }

}
