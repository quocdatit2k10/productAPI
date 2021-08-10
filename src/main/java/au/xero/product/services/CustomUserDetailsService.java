package au.xero.product.services;

import au.xero.product.models.User;
import au.xero.product.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Get information User
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Load user by username
     * @param username
     * @return User
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user==null) new UsernameNotFoundException("User not found");
        return user;
    }

    /**
     * Load user by Id
     * @param id
     * @return User
     */
    @Transactional
    public User loadUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) new UsernameNotFoundException("User not found");
        return user.orElse(null);

    }
}
