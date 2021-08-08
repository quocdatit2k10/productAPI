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
import java.util.Optional;

/**
 * Contains method Product controller
 */
@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    /**
     * Create or update product
     * @param product
     * @param result
     * @return Result product entity
     */
    @PostMapping("")
    public ResponseEntity<?> createOrUpdateProduct(@Valid @RequestBody Product product, BindingResult result) {

        try {
            ResponseEntity<?> mapError = mapValidationErrorService.MapValidationService(result);
            if ((mapError != null)) return mapError;

            Product doProduct = productService.saveOrUpdateProduct(product);

            return ResponseHandler.generateResponse(null, HttpStatus.OK, doProduct);

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
            List<Product> result = productService.getListProduct(name);

            if ((result).size() == 0) {
                return ResponseHandler.generateResponse(PropertiesUtil.getProperty(Constant.product.NO_DATA), HttpStatus.OK);
            }
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
            Optional<Product> result = productService.getProductById(productId);

            if (!result.isPresent()) {
                return ResponseHandler.generateResponse(PropertiesUtil.getProperty(
                        Constant.product.NOT_FOUND, new Object[] {productId}), HttpStatus.OK);
            }

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
            Optional<Product> product = productService.getProductById(productId);

            if (!product.isPresent()) {
                return ResponseHandler.generateResponse(PropertiesUtil.getProperty(
                        Constant.product.NOT_FOUND, new Object[] {productId}), HttpStatus.OK);
            }
            productService.deleteProduct(product.orElseGet(null));
            return ResponseHandler.generateResponse((
                    PropertiesUtil.getProperty(Constant.product.DELETE, new Object[] {productId})), HttpStatus.OK);
        } catch (Exception ex) {

            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}
