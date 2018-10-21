package pk.habsoft.demo.estore.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pk.habsoft.demo.estore.core.Endpoints;
import pk.habsoft.demo.estore.dto.LoginRequest;

@RestController
@RequestMapping(value = Endpoints.Auth.BASE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserAuthenticationController {

    private static final Log LOGGER = LogFactory.getLog(UserAuthenticationController.class);

    /*
     * This is just entry point (/auth/login) of login request. This method
     * won't return anything. Authentication filter will intercept this request
     * and return response.
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.fz.epms.controller.impl.UserAuthenticationController#login(com.fz.
     * epms.empro.model.dto.LoginRequest,
     * org.springframework.mobile.device.Device)
     */
    @RequestMapping(value = Endpoints.Auth.LOGIN, method = RequestMethod.POST)
    public String login(@RequestBody LoginRequest req, Device device) throws AuthenticationException {
        LOGGER.debug(String.format("Login request from %s and device : %s ", req.getUser(), device));
        // Authentication filter will process user authentication
        return "this-wont-be-called";

    }

}