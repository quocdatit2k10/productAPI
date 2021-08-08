package au.xero.product.services;

import au.xero.product.common.Constant;
import au.xero.product.common.Message;
import au.xero.product.common.PropertiesUtil;
import au.xero.product.dto.Product;
import au.xero.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
