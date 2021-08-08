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

        ResponseEntity<?> mapError = mapValidationErrorService.MapValidationService(result);
        if ((mapError != null)) return mapError;

        Product doProduct = productService.saveOrUpdateProduct(product);

        return new ResponseEntity<Product>(doProduct, HttpStatus.OK);
    }

    /**
     * Gets all products or by name
     * @param Product name
     * @Return List product
     */
    @GetMapping("")
    public Iterable<Product> getAllProducts(@RequestParam(required = false) String name) {

        return productService.getListProduct(name);
    }

    /**
     * Gets product by Id
     * @Return Product
     */
    @GetMapping("/{productId}")
    public Optional<Product> getProductById(@PathVariable String productId) {

        return productService.getProductById(productId);
    }

}
