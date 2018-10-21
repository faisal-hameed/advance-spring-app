package pk.habsoft.demo.estore.controller.dummy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pk.habsoft.demo.estore.core.Endpoints;
import pk.habsoft.demo.estore.dto.LoginRequest;

@RestController
@RequestMapping(value = Endpoints.Auth.BASE_URI)
public class UserAuthenticationControllerImpl {

    private static final Log LOGGER = LogFactory.getLog(UserAuthenticationControllerImpl.class);

    /**
     * This is just entry point (/auth/login) of login request. This method
     * won't return anything. Authentication filter will intercept this request
     * and return response.
     * 
     * @param LoginRequest
     *            (user, password)
     * @return
     * 
     * @throws AuthenticationException
     */
    @RequestMapping(value = Endpoints.Auth.LOGIN, method = RequestMethod.POST)
    public String login(@RequestBody LoginRequest req) throws AuthenticationException {
        LOGGER.debug(String.format("Login request from %s and device : %s ", req.getUser()));
        // Authentication filter will process user authentication
        return "this-wont-be-called";

    }

}
