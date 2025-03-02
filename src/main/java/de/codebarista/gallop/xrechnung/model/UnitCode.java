package de.codebarista.gallop.xrechnung.model;

/**
 * Unit codes as defined in Codes for Passengers, Types of Cargo, Packages and Packaging Materials
 * (with Complementary Codes for Package Names) (UN/ECE Recommendation N°21)
 * <p>
 * Version: 3 from 26.04.2022
 * <p>
 * <a href="https://www.xrepository.de/details/urn:xoev-de:kosit:codeliste:rec21_3#version">From XRepository Webpage</a>
 * <p>
 * Note that there is also the Codes for Units of Measure Used in International Trade (UN/ECE Recommendation N°20) but
 * the XRechnung exam
 * <p>
 * The constants in this class are not an exhaustive list.
 */
public class UnitCode {
    /**
     * A loose or unpacked article.
     * <p>
     * It's the unit used in most examples of the <a href="https://projekte.kosit.org/api/v4/projects/356/packages/maven/de/xeinkauf/xrechnung-3.0.2-bundle/2024-06-20/xrechnung-3.0.2-bundle-2024-06-20.zip">XRechnung Bundle 3.0.2</a>.
     * <p>
     * There are about 5 different unit codes for "piece" in UN/ECE N°20 and N°21.
     * While the XRechnung examples use "XPP", the ZUGFerD examples use "H87" and the Mustang project
     * suggests "C67" in the javadoc of the Product class. We decided to use "XPP" from the XRechnung examples.
     */
    public final static String PIECE = "XPP";
}
