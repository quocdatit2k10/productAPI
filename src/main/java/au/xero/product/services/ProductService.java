package au.xero.product.services;

import au.xero.product.common.Constant;
import au.xero.product.common.Message;
import au.xero.product.common.PropertiesUtil;
import au.xero.product.dto.Product;
import au.xero.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static au.xero.product.Validations.Validation.isUUID;

/**
 * Contains method Product service
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Create update product
     * @param product Dto
     * @return Result create table Product
     */
    public Product createProduct(Product product) {

        try {
            // Create product
            return productRepository.save(product);
        } catch (Exception ex) {

            throw new Message(ex.getMessage());
        }
    }


    /**
     * Create and update product
     * @param product Dto
     * @return Result create or update table Product
     */
    public Product updateProduct(Product product, String productId) {

        try {
            Product getProduct;
            // Check exist Id
            if(productId != null) {
                // Check product Id exist or not
                getProduct = getProductById(productId);
                // If product id dose not exist
                if (getProduct.equals(null)) {
                    throw new Message(PropertiesUtil.getProperty(Constant.product.NOT_FOUND, new Object[] {productId}));
                }
            }
            // Update product
            product.setId(UUID.fromString(productId));
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
    public List<Product> getListProduct(String name) {

        List<Product> listProduct;
        try {
            // If name is not null then find by name
            if (name != null) {
                listProduct = productRepository.findByName(name);
            }
            // Find all
            else {
                listProduct = productRepository.findAll();
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
    public Product getProductById(String id) {

        try {
            // Check is UUID
            if (!isUUID(id)) {
                throw new Message(PropertiesUtil.getProperty(Constant.product.CHECK_FORMAT_UUID));
            }
            Product product = productRepository.findById(UUID.fromString(id));

            if (product == null) {
                throw new Message(PropertiesUtil.getProperty(
                        Constant.product.NOT_FOUND, new Object[] {id}));
            }
            // Return product
            return product;
        } catch (Exception ex) {

            throw new Message(ex.getMessage());
        }
    }

    /**
     * Delete product by Id
     * @param  product
     * @return Status
     */
    public void deleteProduct(Product product) {

        try {
            // Delete product
            productRepository.delete(product);
        } catch (Exception ex) {

            throw new Message(ex.getMessage());
        }

    }

}
