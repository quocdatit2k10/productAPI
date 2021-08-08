package au.xero.product.repositories;

import au.xero.product.dto.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contains method Product repository
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByName(String name);

    List<Product> findAll();

}
