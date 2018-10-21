package pk.habsoft.demo.estore.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mobile.device.Device;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import pk.habsoft.demo.estore.db.entity.UserEntity;

@Component
public class AuthTokenService implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    private static final Log LOGGER = LogFactory.getLog(AuthTokenService.class);

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";
    static final String CLAIM_KEY_ROLES = "roles";

    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";

    private String secret = "my-top-secret";

    public static final Long EXPIRATION = 7 * 24 * 60 * 60L; // 7 days for now
    public static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;

    private static final Cache REST_API_TOKEN_CACHE = CacheManager.getInstance().getCache("restApiAuthTokenCache");

    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    public void evictExpiredTokens() {
        LOGGER.info("Evicting expired tokens");
        REST_API_TOKEN_CACHE.evictExpiredElements();
    }

    public void store(String token, Authentication authentication) {
        REST_API_TOKEN_CACHE.put(new Element(token, authentication));

    }

    public boolean contains(String token) {
        return REST_API_TOKEN_CACHE.get(token) != null;
    }

    public Authentication retrieve(String token) {
        return (Authentication) REST_API_TOKEN_CACHE.get(token).getObjectValue();
    }

    public String generateToken(UserEntity userDetails, Device device) {
        LOGGER.debug("Generate new token : " + userDetails.getEmail());
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_AUDIENCE, generateAudience(device));
        // claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_ROLES, mapToRoles(userDetails));

        return generateToken(claims);
    }

    public Boolean validateToken(String token, UserEntity userDetails) throws AuthenticationException {
        if (userDetails == null) {
            return false;
        }
        UserEntity user = (UserEntity) userDetails;
        final String email = getPrincipalFromToken(token);

        final Date created = getCreatedDateFromToken(token);
        return email.equals(user.getEmail()) && !isTokenExpired(token);
    }

    public String getPrincipalFromToken(String token) throws AuthenticationException {
        final Claims claims = getClaimsFromToken(token);
        String principal = claims.getSubject();
        if (principal == null) {
            throw new AuthenticationCredentialsNotFoundException("Principal not found in token.");
        }
        return principal;
    }

    /***********************************************************/

    private Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    private Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    private Claims getClaimsFromToken(String token) throws AuthenticationException {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException(
                    "Unable to parse claims from token : " + e.getMessage(), e);
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRATION * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration == null || expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device == null) {
            return AUDIENCE_UNKNOWN;
        }
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    private List<String> mapToRoles(UserEntity userDetails) {

        return userDetails.getAuthorities().stream().map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
    }

    String generateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public static void main(String[] args) {
        AuthTokenService tokenService = new AuthTokenService();
        UserEntity usr = new UserEntity(0L, "faisalhameedniazi@gmail.com", "tmp", "FB", null);
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtb2NrZWRAZW1haWwuY29tIiwiYXVkaWVuY2UiOiJ1bmtub3duIiwiY3JlYXRlZCI6MTUxNTUxOTY2MTUyMCwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImV4cCI6MTUxNjEyNDQ2MX0.UJ5aPoFfW1xptfbWj8kJSgh-u80MfdahN-u8NcvyWWL2pbiP6pxOJEkIyO5HDrqq50a-G-Wa5UVH8orC1dQkCA";
        // tokenService.generateToken(usr, null);
        System.out.println(token);

        Claims claims = tokenService.getClaimsFromToken(token);
        System.out.println(claims);
        System.out.println("Creted : " + (new Date((Long) claims.get("created"))));
        System.out.println("Expiry : " + tokenService.getExpirationDateFromToken(token));

        System.out.println(tokenService.isTokenExpired(token));

    }

}