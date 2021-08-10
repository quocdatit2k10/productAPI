package au.xero.product.repositories;

import au.xero.product.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Contains method User repository
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
    User findByUsername(String username);
}
