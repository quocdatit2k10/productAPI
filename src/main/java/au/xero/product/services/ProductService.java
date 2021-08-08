package au.xero.product.services;

import au.xero.product.Validations.Validation;
import au.xero.product.common.Constant;
import au.xero.product.common.Message;
import au.xero.product.common.PropertiesUtil;
import au.xero.product.dto.Product;
import au.xero.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Contains method Product service
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Create and update product
     * @param product Dto
     * @return Result create or update table Product
     */
    public Product saveOrUpdateProduct(Product product) {

        try {
            // Check exist Id
            Long productId = product.getId();
            if(productId != null) {
                Optional<Product>  checkProductExist = productRepository.findById(productId);
                if (!checkProductExist.isPresent()) {
                    throw new Message(PropertiesUtil.getProperty(Constant.product.NOT_FOUND, new Object[] {productId}));
                }
            }
            return productRepository.save(product);
        } catch (Exception ex) {
            throw new Message(ex.getMessage());
        }
    }

    /**
     * Find all product
     * @param name
     * @return list product
     */
    public Iterable<Product> getListProduct(String name) {

        Iterable<Product> listProduct;
        try {
            if (name != null) {
                listProduct = productRepository.findByName(name);
            }
            else {
                listProduct = productRepository.findAll();
            }

            if (((ArrayList) listProduct).size() == 0) {
                throw new Message(PropertiesUtil.getProperty(Constant.product.NO_DATA));
            }

            return listProduct;
        } catch (Exception ex) {
            throw new Message(ex.getMessage());
        }
    }


    /**
     * Find product by Id
     * @param id
     * @return Product
     */
    public Optional<Product> getProductById(String id) {

        try {
            if(!Validation.isNumeric(id)) {
                throw new Message(PropertiesUtil.getProperty(Constant.product.MUST_NUMBER, new Object[] {id}));
            }
            Optional<Product> product = productRepository.findById(Long.parseLong(id));
            if (!product.isPresent()) {
                throw new Message(PropertiesUtil.getProperty(Constant.product.NOT_FOUND, new Object[] {id}));
            }
            return product;
        } catch (Exception ex) {
            throw new Message(ex.getMessage());
        }
    }

}
