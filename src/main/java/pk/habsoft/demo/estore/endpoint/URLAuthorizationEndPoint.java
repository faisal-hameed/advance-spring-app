package pk.habsoft.demo.estore.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Endpoints.UrlAuthorization.BASE_URL)
public class URLAuthorizationEndPoint {

    @RequestMapping(value = Endpoints.UrlAuthorization.USER_PAGE, method = RequestMethod.GET)
    public String testUserRole() {
        return "Only users can access this page";
    }

    @RequestMapping(value = Endpoints.UrlAuthorization.ADMIN_PAGE, method = RequestMethod.GET)
    public String testAdminRole() {
        return "Only admins can access this page";
    }

    @RequestMapping(value = Endpoints.UrlAuthorization.COMMON_PAGE, method = RequestMethod.GET)
    public String testCommonRole() {
        return "Anyone can access this page";
    }

}
