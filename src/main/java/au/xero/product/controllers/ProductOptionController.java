package au.xero.product.controllers;

import au.xero.product.common.Constant;
import au.xero.product.common.PropertiesUtil;
import au.xero.product.common.ResponseHandler;
import au.xero.product.dto.Product;
import au.xero.product.dto.ProductOption;
import au.xero.product.services.MapValidationErrorService;
import au.xero.product.services.ProductOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Contains method product option
 */
@RestController
@RequestMapping("/products")
public class ProductOptionController {

    @Autowired
    private ProductOptionService productOptionService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    /**
     * Create product option
     * @param productOption
     * @param result
     * @return Result product option entity
     */
    @PostMapping("/{productId}/options")
    public ResponseEntity<?> createProductOption(
            @PathVariable String productId,
            @Valid @RequestBody ProductOption productOption,
            BindingResult result) {

        try {
            // Check error of setting DTO
            ResponseEntity<?> mapError = mapValidationErrorService.MapValidationService(result);
            if ((mapError != null)) return mapError;

            // Generate message of create
            String mgs = PropertiesUtil.getProperty(Constant.productOption.CREATE);
            // Action create or update
            ProductOption doProductOption = productOptionService.createProductOption(productId, productOption);

            // Return result action
            return ResponseHandler.generateResponse(mgs, HttpStatus.OK, doProductOption);

        } catch (Exception ex) {

            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update product option
     * @param productOption
     * @param result
     * @return Result product option entity
     */
    @PutMapping("/{productId}/options/{productOptionId}")
    public ResponseEntity<?> updateProductOption(
            @PathVariable String productId,
            @Valid @RequestBody ProductOption productOption,
            @PathVariable String productOptionId,
            BindingResult result) {

        try {
            // Check error of setting DTO
            ResponseEntity<?> mapError = mapValidationErrorService.MapValidationService(result);
            if ((mapError != null)) return mapError;

            // Generate message of update
            String mgs = PropertiesUtil.getProperty(
                    Constant.productOption.UPDATE, new Object[] {productOptionId});
            // Action create or update
            ProductOption doProductOption = productOptionService.updateProductOption(productId, productOptionId, productOption);

            // Return result action
            return ResponseHandler.generateResponse(mgs, HttpStatus.OK, doProductOption);

        } catch (Exception ex) {

            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
