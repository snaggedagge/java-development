package dkarlsso.portal.facebook;

import dkarlsso.portal.model.User;
import dkarlsso.portal.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String execute(Connection<?> connection) {
        User user = new User();
        user.setFacebookId(connection.getKey().getProviderUserId());
        user.setUsername(connection.getDisplayName());

        if(!userRepository.existsByFacebookId(user.getFacebookId())) {
            userRepository.save(user);
        }

        return user.getUsername();
    }
}