package pk.habsoft.demo.estore.service.impl;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import pk.habsoft.demo.estore.service.TokenService;

public class TokenServiceImpl implements TokenService {

    private static final Logger LOGGER = Logger.getLogger(TokenServiceImpl.class);
    private static final Cache REST_API_AUTH_TOKEN_CACHE = CacheManager.getInstance().getCache("restApiAuthTokenCache");
    public static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;

    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    public void evictExpiredTokens() {
        LOGGER.info("Evicting expired tokens");
        REST_API_AUTH_TOKEN_CACHE.evictExpiredElements();
    }

    /*
     * (non-Javadoc)
     * 
     * @see pk.habsoft.demo.estore.service.impl.TokenService#generateNewToken()
     */
    @Override
    public String generateNewToken() {
        return UUID.randomUUID().toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * pk.habsoft.demo.estore.service.impl.TokenService#store(java.lang.String,
     * org.springframework.security.core.Authentication)
     */
    @Override
    public void store(String token, Authentication authentication) {
        REST_API_AUTH_TOKEN_CACHE.put(new Element(token, authentication));
    }

    /*
     * (non-Javadoc)
     * 
     * @see pk.habsoft.demo.estore.service.impl.TokenService#contains(java.lang.
     * String)
     */
    @Override
    public boolean contains(String token) {
        return REST_API_AUTH_TOKEN_CACHE.get(token) != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see pk.habsoft.demo.estore.service.impl.TokenService#retrieve(java.lang.
     * String)
     */
    @Override
    public Authentication retrieve(String token) {
        return (Authentication) REST_API_AUTH_TOKEN_CACHE.get(token).getObjectValue();
    }
}