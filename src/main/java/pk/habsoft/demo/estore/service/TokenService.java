package pk.habsoft.demo.estore.service;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class TokenService {

	private static final Logger LOGGER = Logger.getLogger(TokenService.class);
	private static final Cache REST_API_AUTH_TOKEN_CACHE = CacheManager.getInstance().getCache("restApiAuthTokenCache");
	public static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;

	@Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
	public void evictExpiredTokens() {
		LOGGER.info("Evicting expired tokens");
		REST_API_AUTH_TOKEN_CACHE.evictExpiredElements();
	}

	public String generateNewToken() {
		return UUID.randomUUID().toString();
	}

	public void store(String token, Authentication authentication) {
		REST_API_AUTH_TOKEN_CACHE.put(new Element(token, authentication));
	}

	public boolean contains(String token) {
		return REST_API_AUTH_TOKEN_CACHE.get(token) != null;
	}

	public Authentication retrieve(String token) {
		return (Authentication) REST_API_AUTH_TOKEN_CACHE.get(token).getObjectValue();
	}
}