package au.xero.product.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class MessageHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public final ResponseEntity<Object> handlerMessageException(Message mg, WebRequest webRequest) {

        MessageResponse exceptionResponse = new MessageResponse(mg.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
