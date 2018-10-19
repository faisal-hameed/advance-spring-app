package pk.habsoft.demo.estore.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

import pk.habsoft.demo.estore.dto.LoginRequest;
import pk.habsoft.demo.estore.endpoint.Endpoints;

public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class);

    public static final String TOKEN_SESSION_KEY = "token";
    public static final String USER_SESSION_KEY = "user";
    private static final String TOKEN_HEADER = "X-AUTH-TOKEN";

    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String api = request.getRequestURI();
        LOGGER.info("Authentication filter : " + api);

        try {
            if (postToAuthenticate(request, api)) {
                // Skip auth URL, it will be handled by
                // AccountAuthenticationProvider
                ObjectMapper mapper = new ObjectMapper();
                LoginRequest loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);
                LOGGER.debug(String.format("Trying to authenticate user %s ", loginRequest.getUser()));
                processUsernamePasswordAuthentication(response, loginRequest);
                LOGGER.debug("User authentication completed.");
                return;
            }

            Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));
            // If token is present then authenticate with token
            if (token.isPresent()) {
                LOGGER.debug("Trying to authenticate with token.");
                tryToAuthenticateWithToken(token.get());
            }

            LOGGER.debug("AuthenticationFilter is passing request down the filter chain");
            addSessionContextToLogging();
            // Now continue with filters chain
            chain.doFilter(request, response);

        } catch (AuthenticationException e) {
            LOGGER.error("AuthenticationException in AuthenticationFilter : " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        } finally {
            MDC.remove(TOKEN_SESSION_KEY);
            MDC.remove(USER_SESSION_KEY);
        }

    }

    private void addSessionContextToLogging() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String tokenValue = "EMPTY";
        if (authentication != null && authentication.getDetails() != null) {
            MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("SHA-1");
            tokenValue = encoder.encodePassword(authentication.getDetails().toString(), "not_so_random_salt");
        }
        MDC.put(TOKEN_SESSION_KEY, tokenValue);

        String userValue = "EMPTY";
        if (authentication != null && !Strings.isNullOrEmpty(authentication.getPrincipal().toString())) {
            userValue = authentication.getPrincipal().toString();
        }
        MDC.put(USER_SESSION_KEY, userValue);
    }

    /**
     * This method will write LoginResponse on outpustream.
     * 
     * @param httpResponse
     * @param loginRequest
     * @throws IOException
     */
    private void processUsernamePasswordAuthentication(HttpServletResponse httpResponse, LoginRequest loginReq)
            throws IOException {

        UsernamePasswordAuthenticationToken requestAuthentication = new UsernamePasswordAuthenticationToken(
                loginReq.getUser(), loginReq.getPassword());

        UsernamePasswordAuthenticationToken resultOfAuthentication = (UsernamePasswordAuthenticationToken) tryToAuthenticate(
                requestAuthentication);

        // Reload password post-security so we can generate token
        final String token = resultOfAuthentication.getDetails().toString();

        // Only write body
        String tokenJsonResponse = new ObjectMapper().writeValueAsString(token);
        httpResponse.addHeader("Content-Type", "application/json");
        httpResponse.getWriter().print(tokenJsonResponse);
    }

    private Authentication tryToAuthenticateWithToken(String token) {
        PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(token,
                null);
        return tryToAuthenticate(requestAuthentication);
    }

    private Authentication tryToAuthenticate(Authentication requestAuthentication) {
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new InternalAuthenticationServiceException(
                    "Unable to authenticate Domain User for provided credentials");
        }
        SecurityContextHolder.getContext().setAuthentication(responseAuthentication);
        logger.debug("User successfully authenticated");
        return responseAuthentication;
    }

    private boolean postToAuthenticate(HttpServletRequest httpRequest, String resourcePath) {
        return (Endpoints.CONTEXT_PATH + Endpoints.Auth.BASE_URL + Endpoints.Auth.LOGIN).equalsIgnoreCase(resourcePath)
                && HttpMethod.POST.matches(httpRequest.getMethod());
    }
}