package au.xero.product.repositories;

import au.xero.product.dto.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Contains method Product repository
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
