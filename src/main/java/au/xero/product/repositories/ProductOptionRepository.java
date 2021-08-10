package au.xero.product.repositories;

import au.xero.product.models.ProductOption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Contains method Product option repository
 */
@Repository
public interface ProductOptionRepository extends CrudRepository<ProductOption, Long> {

    ProductOption findByIdAndProductId(UUID id, UUID productId);

    List<ProductOption> findByProductId(UUID productId);
}
