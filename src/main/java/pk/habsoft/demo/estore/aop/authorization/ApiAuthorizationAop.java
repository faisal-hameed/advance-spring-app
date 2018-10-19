package pk.habsoft.demo.estore.aop.authorization;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import pk.habsoft.demo.estore.db.entity.UserEntity;
import pk.habsoft.demo.estore.exceptions.ApiAuthorizationException;
import pk.habsoft.demo.estore.security.AuthenticationWithToken;

/**
 * The Class ApiAuthorizationAop.
 */
@Aspect
@Component
public class ApiAuthorizationAop {

	/** The log. */
	private static final Logger LOGGER = LogManager.getLogger(ApiAuthorizationAop.class);

	/**
	 * Check api permission.
	 *
	 * @param joinPoint
	 *            the join point
	 * @param apiAccess
	 *            the api access
	 */
	@Before("@annotation(apiAccess)")
	public void checkApiPermission(JoinPoint joinPoint, ApiAuthorization apiAccess) {
		LOGGER.debug("Check API access");
		try {
			UserEntity usr = getLoggedinUser();

			if (Arrays.stream(apiAccess.permissions()).anyMatch(usr::hasPermission)) {
				// User has proper role
				return;
			}
			Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
			LOGGER.info(
					String.format("User (%s) is not authorizaed to call API (%s)", usr.getName(), method.getName()));
		} catch (Exception e) {
			LOGGER.error("Error checking API authorization.", e);
		}
		throw new ApiAuthorizationException("You are not authorized to invoke this API");
	}

	/**
	 * Gets the loggedin user.
	 *
	 * @return the loggedin user
	 */
	private UserEntity getLoggedinUser() {
		AuthenticationWithToken authentication = (AuthenticationWithToken) SecurityContextHolder.getContext()
				.getAuthentication();
		return authentication.getJwtUser();
	}
}