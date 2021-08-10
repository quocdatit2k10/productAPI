package au.xero.product.validations;

import au.xero.product.models.User;
import org.springframework.validation.Errors;

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


    public void validate(Object object, Errors errors) {

        User user = (User) object;

        if(user.getPassword().length() <6){
            errors.rejectValue("password","Length", "Password must be at least 6 characters");
        }

        if(!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("confirmPassword","Match", "Passwords must match");

        }
    }

}
