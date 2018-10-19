package pk.habsoft.demo.estore.security;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import pk.habsoft.demo.estore.service.TokenService;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = Logger.getLogger(TokenAuthenticationProvider.class);

    private TokenService tokenService;

    public TokenAuthenticationProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LOGGER.debug("Invoking... TokenAuthenticationProvider");
        String token = (String) authentication.getPrincipal();

        if (tokenService.contains(token)) {
            LOGGER.info("User authenticated with token");
            return tokenService.retrieve(token);
        } else {
            throw new BadCredentialsException("Invalid token or token expired");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}