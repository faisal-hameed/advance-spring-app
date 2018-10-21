package pk.habsoft.demo.estore.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import pk.habsoft.demo.estore.db.entity.UserEntity;
import pk.habsoft.demo.estore.security.AuthenticationWithToken;

/**
 * The Class AppUserDetailsService.
 */
@Component
public class AppUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LogManager.getLogger(AppUserDetailsService.class);
    /** The users. */
    @Autowired
    UserService userService;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetailsService#
     * loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("DetailsService::loadUserByUsername() : " + username);
        UserEntity user = userService.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " was not found");
        }
        return user;
    }

    /**
     * Authenticate.
     *
     * @param username
     *            the username
     * @param password
     *            the password
     * @return the authentication
     */
    public AuthenticationWithToken authenticate(String username, String rawPassword) {
        if (username == null || rawPassword == null) {
            throw new BadCredentialsException("Credentials can't be empty.");
        }

        UserEntity jwtUser = (UserEntity) loadUserByUsername(username);
        String encPass = userService.getPassEncoder().encode(rawPassword);
        if (jwtUser == null || (encPass.equals(jwtUser.getPassword()))) {
            throw new BadCredentialsException("Invalid user's credentials.");
        }
        LOGGER.debug("User authenticated : " + username);
        AuthenticationWithToken auth = new AuthenticationWithToken(username, rawPassword, jwtUser.getAuthorities());
        auth.setJwtUser(jwtUser);

        return auth;
    }

}