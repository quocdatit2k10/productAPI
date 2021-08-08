package au.xero.product.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Show error message
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class Message extends RuntimeException {

    public Message(String message) {
        super(message);
    }
}
