package pk.habsoft.demo.estore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fz.epms.controller.form.ChangePasswordForm;
import com.fz.epms.controller.form.CreateUserForm;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import pk.habsoft.demo.estore.core.Endpoints;
import pk.habsoft.demo.estore.core.ResponseBuilder;
import pk.habsoft.demo.estore.service.UserService;

@RestController
@RequestMapping(value = Endpoints.User.BASE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "user_controller")
public class UserController {

    @Autowired
    UserService manager;

    @RequestMapping(method = RequestMethod.GET, value = Endpoints.User.GET_USER_BY_ID)
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "Authorization token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<?> getUserById(@PathVariable int userId) {
        return ResponseBuilder.success(manager.getUserById(userId));
    }

    @RequestMapping(method = RequestMethod.POST, value = Endpoints.User.CREATE_USER)
    // @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "Authorization token",
    // required = true, dataType = "string", paramType = "header")
    public ResponseEntity<?> createUser(@RequestBody CreateUserForm req) {
        return ResponseBuilder.success(manager.createUser(req));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = Endpoints.User.DELETE_USER_BY_ID)
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "Authorization token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        return ResponseBuilder.success(manager.deleteUser(id));
    }

    @RequestMapping(method = RequestMethod.POST, value = Endpoints.User.CHANGE_PASSWORD)
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "Authorization token", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordForm req) {
        return ResponseBuilder.success(manager.changePassword(req));
    }

}