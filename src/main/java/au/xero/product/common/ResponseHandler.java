package au.xero.product.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Show message info
 */
public class ResponseHandler {

    /**
     * Show message info
     * @param message
     * @param status
     * @return message
     */
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (message != null) {
            map.put("message", message);
        }

        return new ResponseEntity<Object>(map,status);
    }

    /**
     * Show message info
     * @param message
     * @param status
     * @param responseObj
     * @return message
     */
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (message != null) {
            map.put("message", message);
        }
        if (responseObj != null) {
            map.put("data", responseObj);
        }

        return new ResponseEntity<Object>(map,status);
    }
}
