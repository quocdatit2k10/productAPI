package au.xero.product.services;

import au.xero.product.common.Constant;
import au.xero.product.common.Message;
import au.xero.product.common.PropertiesUtil;
import au.xero.product.models.User;
import au.xero.product.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Contains method User service
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Create User
     * @param newUser
     * @return User
     */
    public User saveUser (User newUser){

        try{

            // If user name is exist
            if(userRepository.findByUsername(newUser.getUsername()) != null) {
                throw new Message(PropertiesUtil.getProperty(Constant.user.USER_EXIST, new Object[] {newUser.getUsername()}));
            }

            if(newUser.getPassword().length() < 5){
                throw new Message(PropertiesUtil.getProperty(Constant.user.USER_MAXLEGTH));
            }

            if(!newUser.getPassword().equals(newUser.getConfirmPassword())){
                throw new Message(PropertiesUtil.getProperty(Constant.user.USER_CONFIRM_PASSWORD));
            }

            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);

        }catch (Exception e){

            throw new Message(e.getMessage());
        }

    }

}
