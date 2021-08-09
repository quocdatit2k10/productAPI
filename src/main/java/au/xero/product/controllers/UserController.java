package au.xero.product.controllers;


import au.xero.product.Validations.Validation;
import au.xero.product.common.Constant;
import au.xero.product.common.PropertiesUtil;
import au.xero.product.common.ResponseHandler;
import au.xero.product.dto.Product;
import au.xero.product.dto.User;
import au.xero.product.services.MapValidationErrorService;
import au.xero.product.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Contains method Product controller
 */
@RestController
public class UserController {


    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){

        try {
            // Check error of setting DTO
            ResponseEntity<?> mapError = mapValidationErrorService.MapValidationService(result);
            if ((mapError != null)) return mapError;

            // Action create
            User newUser = userService.saveUser(user);

            // Return result action
            return ResponseHandler.generateResponse(PropertiesUtil.getProperty(Constant.user.USER_CREATE), HttpStatus.OK, newUser);

        } catch (Exception ex) {

            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
