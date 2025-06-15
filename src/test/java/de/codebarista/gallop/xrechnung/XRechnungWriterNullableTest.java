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
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create())).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentInstructions() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()))
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentCreditTransfer() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()
                        .creditTransfer(CreditTransfer.create())))
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentCardInformation() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()
                        .paymentCardInformation(PaymentCardInformation.create())))
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPaymentCardInstructions() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .paymentInstructions(PaymentInstructions.create()
                        .directDebit(DirectDebit.create())))
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptySeller() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .seller(SellerOrBuyer.create()))
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPostalAddress() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .seller(SellerOrBuyer.create()
                        .address(PostalAddress.create())))
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyContact() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .seller(SellerOrBuyer.create()
                        .contact(Contact.create())))
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyBuyer() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .buyer(SellerOrBuyer.create()))
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyDeliveryInformation() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .deliveryInfo(DeliveryInformation.create()))
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyDeliveryInformationPostAddress() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .deliveryInfo(DeliveryInformation.create()
                        .deliveryAddress(PostalAddress.create())))
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyItem() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .item(Item.create()))
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyItemVat() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .item(Item.create()
                        .vat(Vat.create())))
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyVatTotal() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .vatTotal(Vat.create()))
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyPrecedingInvoiceReference() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .precedingInvoiceReference(PrecedingInvoiceReference.create()))
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyInvoiceNote() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .invoiceNote(InvoiceNote.of(null)))
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyAllowance() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .allowance(Allowance.create()))
        ).isNotEmpty();
    }

    @Test
    public void doesNotFailWithEmptyCharge() {
        assertThat(XRechnungWriter.generateXRechnungXML(Invoice.create()
                .charge(Charge.create()))
        ).isNotEmpty();
    }

}
