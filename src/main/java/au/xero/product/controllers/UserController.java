package au.xero.product.controllers;


import au.xero.product.common.Constant;
import au.xero.product.common.PropertiesUtil;
import au.xero.product.common.ResponseHandler;
import au.xero.product.models.User;
import au.xero.product.payload.JWTLoginSuccessResponse;
import au.xero.product.payload.LoginRequest;
import au.xero.product.security.JwtTokenProvider;
import au.xero.product.services.MapValidationErrorService;
import au.xero.product.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Authentication
     * @param loginRequest
     * @param result
     * @return Authentication
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {

        try {
            // Check error of setting login request
            ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
            if(errorMap != null) return errorMap;

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = Constant.security.TOKEN_PREFIX +  tokenProvider.generateToken(authentication);

            return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(PropertiesUtil.getProperty(
                    Constant.user.USER_NAME_PASS_INCORRECT), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Register User
     * @param user
     * @param result
     * @return User
     */
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
