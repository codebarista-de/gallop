package de.codebarista.gallop.xrechnung.model;

/**
 * Codes for "means of payment" as defined in UNTDID 4461 (UN/EDIFACT 4461)
 * <p>
 * Version: 3 from 08.02.2021
 * <p>
 * <a href="https://www.xrepository.de/details/urn:xoev-de:xrechnung:codeliste:untdid.4461_3">From XRepository Webpage</a>
 * <p>
 * The constants in this class are not an exhaustive list.
 */
public class PaymentCode {
    /**
     * Payment "In cash"
     * <p>
     * Payment by currency (including bills and coins) in circulation, including checking account deposits.
     */
    public static final String CASH = "10";

    /**
     * SEPA credit transfer
     * <p>
     * Credit transfer inside the Single Euro Payment Area (SEPA) system.
     */
    public static final String SEPA_CREDIT_TRANSFER = "58";

    /**
     * Online payment service
     * <p>
     * Payment will be made or has been made by an online payment service.
     */
    public static final String ONLINE_PAYMENT_SERVICE = "68";
}
