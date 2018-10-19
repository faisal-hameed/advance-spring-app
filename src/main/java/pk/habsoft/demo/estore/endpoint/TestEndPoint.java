package pk.habsoft.demo.estore.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Endpoints.Test.BASE_URL)
public class TestEndPoint {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String testAPI() {
        return "Service is running";
    }

}
