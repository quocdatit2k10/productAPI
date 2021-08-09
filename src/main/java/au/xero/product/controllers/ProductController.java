package au.xero.product.controllers;

import au.xero.product.common.Constant;
import au.xero.product.common.PropertiesUtil;
import au.xero.product.common.ResponseHandler;
import au.xero.product.dto.Product;
import au.xero.product.services.MapValidationErrorService;
import au.xero.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Contains method Product controller
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    /**
     * Create product
     * @param product
     * @param result
     * @return Result product entity
     */
    @PostMapping("")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult result) {

        try {
            // Check error of setting DTO
            ResponseEntity<?> mapError = mapValidationErrorService.MapValidationService(result);
            if ((mapError != null)) return mapError;

            // Generate message of create
            String mgs = PropertiesUtil.getProperty(Constant.product.CREATE);

            // Action create
            Product doProduct = productService.createProduct(product);

            // Return result action
            return ResponseHandler.generateResponse(mgs, HttpStatus.OK, doProduct);

        } catch (Exception ex) {

            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Update product
     * @param product
     * @param result
     * @return Result product entity
     */
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody Product product,
                                           @PathVariable String productId, BindingResult result) {

        try {
            // Check error of setting DTO
            ResponseEntity<?> mapError = mapValidationErrorService.MapValidationService(result);
            if ((mapError != null)) return mapError;

            // Generate message of create and update
            String mgs = PropertiesUtil.getProperty(
                    Constant.product.UPDATE, new Object[] {productId});
            // Action update
            Product doProduct = productService.updateProduct(product, productId);

            // Return result action
            return ResponseHandler.generateResponse(mgs, HttpStatus.OK, doProduct);

        } catch (Exception ex) {

            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Gets all products or by name
     * @param  name
     * @Return List product
     */
    @GetMapping("")
    public ResponseEntity<Object> getAllProducts(@RequestParam(required = false) String name) {

        try {
            // Get list product all or by name
            List<Product> result = productService.getListProduct(name);

            // If size list is zero return message no data
            if ((result).size() == 0) {
                return ResponseHandler.generateResponse(PropertiesUtil.getProperty(Constant.product.NO_DATA), HttpStatus.OK);
            }

            // Return list product
            return ResponseHandler.generateResponse(null, HttpStatus.OK, result);

        } catch (Exception ex) {

            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * Gets product by Id
     * @Return Product
     */
    @GetMapping("/{productId}")
    public ResponseEntity<Object> getProductById(@PathVariable String productId) {

        try {
            // Find product by id
            Product result = productService.getProductById(productId);
            // Return product
            return ResponseHandler.generateResponse(null, HttpStatus.OK, result);

        } catch (Exception ex) {

            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * Delete product by Id
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProject(@PathVariable String productId) {

        try {
            // Find product by id
            Product result = productService.getProductById(productId);
            //Delete product by id
            productService.deleteProduct(result);
            return ResponseHandler.generateResponse((
                    PropertiesUtil.getProperty(Constant.product.DELETE, new Object[] {productId})), HttpStatus.OK);
        } catch (Exception ex) {

            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}
