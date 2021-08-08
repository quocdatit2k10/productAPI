package au.xero.product.controllers;

import au.xero.product.dto.Product;
import au.xero.product.services.MapValidationErrorService;
import au.xero.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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


        ResponseEntity<?> mapError = mapValidationErrorService.MapValidationService(result);
        if ((mapError != null)) return mapError;

        Product doProduct = productService.saveOrUpdateProduct(product);

        return new ResponseEntity<Product>(doProduct, HttpStatus.OK);
    }
}
