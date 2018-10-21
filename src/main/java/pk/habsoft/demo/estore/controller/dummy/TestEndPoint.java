package pk.habsoft.demo.estore.controller.dummy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pk.habsoft.demo.estore.core.Endpoints;

@RestController
@RequestMapping(Endpoints.Test.BASE_URL)
public class TestEndPoint {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String testAPI() {
        return "Service is running";
    }

}
