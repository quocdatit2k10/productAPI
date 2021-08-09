package au.xero.product.Validations;

import java.util.UUID;

/**
 * Validation
 */
public class Validation {

    /**
     * Check is number
     * @param strNum
     * @return Boolean
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Check is uuid
     * @param uuid
     * @return Boolean
     */
    public static boolean isUUID(String uuid) {
        if (uuid == null) {
            return false;
        }
        try {
            UUID uu = java.util.UUID.fromString(uuid);
        } catch (Exception nfe) {
            return false;
        }
        return true;
    }
}
