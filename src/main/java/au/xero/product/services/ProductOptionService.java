package au.xero.product.services;

import au.xero.product.common.Constant;
import au.xero.product.common.Message;
import au.xero.product.common.PropertiesUtil;
import au.xero.product.dto.Product;
import au.xero.product.dto.ProductOption;
import au.xero.product.repositories.ProductOptionRepository;
import au.xero.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static au.xero.product.Validations.Validation.isUUID;

/**
 * Contains method Product option service
 */
@Service
public class ProductOptionService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    /**
     *  Create or update product option by product ID
     * @param productId
     * @param productOption
     * @return productOption
     */
    public ProductOption createProductOption(String productId, ProductOption productOption) {

        try {

            // Check product id exist or not
            Product product = productService.getProductById(productId);

            productOption.setProductId(product.getId());
            // Create or update product option
            return productOptionRepository.save(productOption);

        } catch (Exception ex) {

            throw new Message(ex.getMessage());
        }
    }


    /**
     *  Create or update product option by product ID
     * @param productId
     * @param productOption
     * @return productOption
     */
    public ProductOption updateProductOption(String productId,
                                                   String productOptionId, ProductOption productOption) {

        try {

            // Check product id exist or not
            Product product = productService.getProductById(productId);

            // Check product option id is exist or not
            if(productOptionId != null) {
                // Check product Id exist or not
                ProductOption checkProductOptionExist = getProductOptionById(productOptionId);
                // If product id dose not exist
                if (checkProductOptionExist.equals(null)) {
                    throw new Message(PropertiesUtil.getProperty(Constant.productOption.NOT_FOUND, new Object[] {productOptionId}));
                }
            }
            productOption.setProductId(product.getId());
            // Create or update product option
            return productOptionRepository.save(productOption);

        } catch (Exception ex) {

            throw new Message(ex.getMessage());
        }
    }


    /**
     * Find product option by Id
     * @param id
     * @return Product option
     */
    public ProductOption getProductOptionById(String id) {

        try {
            // Check is UUID
            if (!isUUID(id)) {
                throw new Message(PropertiesUtil.getProperty(Constant.productOption.CHECK_FORMAT_UUID));
            }
            ProductOption productOption = productOptionRepository.findById(UUID.fromString(id));

            if (productOption == null) {
                throw new Message(PropertiesUtil.getProperty(
                        Constant.productOption.NOT_FOUND, new Object[] {id}));
            }
            // Return product
            return productOption;
        } catch (Exception ex) {

            throw new Message(ex.getMessage());
        }
    }
}
