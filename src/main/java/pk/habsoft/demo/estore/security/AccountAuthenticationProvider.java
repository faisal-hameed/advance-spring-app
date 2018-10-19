package pk.habsoft.demo.estore.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import pk.habsoft.demo.estore.service.AppUserDetailsService;
import pk.habsoft.demo.estore.service.TokenService;

public class AccountAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = Logger.getLogger(AccountAuthenticationProvider.class);

    private TokenService tokenService;

    @Autowired
    private AppUserDetailsService userDetailsService;

    public AccountAuthenticationProvider(TokenService tokenService) {
        super();
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LOGGER.debug("Invoking... AccountAuthenticationProvider");
        Object user = authentication.getPrincipal();
        Object password = authentication.getCredentials();

        if (user == null || password == null) {
            throw new BadCredentialsException("Credentials not provided");
        }

        UsernamePasswordAuthenticationToken authWithToken = (UsernamePasswordAuthenticationToken) userDetailsService
                .authenticate(user.toString(), password.toString());

        String token = tokenService.generateNewToken();
        authWithToken.setDetails(token);

        // Save token in cache for subsequent authentication
        tokenService.store(token, authWithToken);

        return authWithToken;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
