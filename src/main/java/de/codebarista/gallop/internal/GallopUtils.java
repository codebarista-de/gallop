package de.codebarista.gallop.internal;

/**
 * Internal helper methods shared across Gallop.
 */
public final class GallopUtils {
    private GallopUtils() {
    }

    /**
     * Checks if a given string is {@code null} or blank.
     *
     * @param string the string to check
     * @return {@code true} if the string is {@code null} or blank, otherwise {@code false}
     */
    public static boolean isNullOrBlank(String string) {
        return string == null || string.isBlank();
    }

    /**
     * Checks if a given string is not {@code null} and not blank.
     *
     * @param string the string to check
     * @return {@code true} if the string is not {@code null} and not blank, otherwise {@code false}
     */
    public static boolean isNotNullOrBlank(String string) {
        return !isNullOrBlank(string);
    }
}
