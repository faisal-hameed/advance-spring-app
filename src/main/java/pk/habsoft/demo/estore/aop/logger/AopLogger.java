package pk.habsoft.demo.estore.aop.logger;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * The Class AopLogger.
 */
@Aspect
@Component

public class AopLogger {

    /** The log. */
    private Logger log = Logger.getLogger(getClass());

    /**
     * Log all method execution before calling the actual methods of all
     * endpoints under this (pk.habsoft.demo.estore.endpoint) package.
     *
     * @param point
     *            the point
     */
    @Before("execution(* pk.habsoft.demo.estore.endpoint.*.*(..))")
    public void log(JoinPoint point) {
        log.info(prepareLogString(point));
    }

    /**
     * Prepare log string.
     *
     * @param point
     *            the point
     * @return the string
     */
    private String prepareLogString(JoinPoint point) {
        StringBuilder audit = new StringBuilder();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // User name
        audit.append(authentication.getPrincipal());
        audit.append(" called -> ");
        // Class name
        String fqnClass = point.getSignature().getDeclaringTypeName();
        audit.append(fqnClass.substring(1 + fqnClass.lastIndexOf('.')));
        audit.append(".");
        audit.append(point.getSignature().getName());
        // Arguments
        audit.append(String.format(" (%s)", Arrays.toString(point.getArgs())));

        return audit.toString();
    }
}