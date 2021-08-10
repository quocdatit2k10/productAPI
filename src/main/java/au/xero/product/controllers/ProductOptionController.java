package au.xero.product.controllers;

import au.xero.product.common.Constant;
import au.xero.product.common.PropertiesUtil;
import au.xero.product.common.ResponseHandler;
import au.xero.product.models.ProductOption;
import au.xero.product.services.MapValidationErrorService;
import au.xero.product.services.ProductOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    /**
     * Gets all products option by product id
     * @param  productId
     * @Return List product option
     */
    @GetMapping("/{productId}/options")
    public ResponseEntity<Object> getAllProductOption(@PathVariable String productId) {

        try {
            // Get list all product option by product id
            List<ProductOption> result = productOptionService.getListProductOption(productId);

            // If size list is zero return message no data
            if ((result).size() == 0) {
                return ResponseHandler.generateResponse(PropertiesUtil.getProperty(Constant.productOption.NO_DATA), HttpStatus.OK);
            }
            // Return list product option
            return ResponseHandler.generateResponse(null, HttpStatus.OK, result);

        } catch (Exception ex) {

            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * Gets all products option by product id and product option id
     * @param  productId
     * @param  productOptionId
     * @Return List product option
     */
    @GetMapping("/{productId}/options/{productOptionId}")
    public ResponseEntity<Object> getProductOptionByIdAndProductId(@PathVariable String productId,
                                                      @PathVariable String productOptionId) {

        try {
            // Get list all product option by product id
            ProductOption result = productOptionService.getProductOptionByIdAndProductId(productId, productOptionId);

            // If size list is zero return message no data
            if (result == null) {
                return ResponseHandler.generateResponse(PropertiesUtil.getProperty(Constant.productOption.NO_DATA), HttpStatus.OK);
            }
            // Return list product option
            return ResponseHandler.generateResponse(null, HttpStatus.OK, result);

        } catch (Exception ex) {

            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * Delete product option by Id
     */
    @DeleteMapping("/{productId}/options/{productOptionId}")
    public ResponseEntity<?> deleteProductOption(@PathVariable String productId,
                                           @PathVariable String productOptionId) {

        try {
            // Get list all product option by product id
            ProductOption result = productOptionService.getProductOptionByIdAndProductId(productId, productOptionId);

            //Delete product option
            productOptionService.deleteProductOption(result);
            return ResponseHandler.generateResponse((
                    PropertiesUtil.getProperty(Constant.productOption.DELETE, new Object[] {productOptionId})), HttpStatus.OK);
        } catch (Exception ex) {

            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
