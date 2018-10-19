package pk.habsoft.demo.estore.endpoint;

public interface Endpoints {

    String CONTEXT_PATH = "/mma";

    public interface Test {
        String BASE_URL = "/test";
    }
    
    public interface Auth {
        String BASE_URL = "/auth";
        String LOGIN = "/login";
    }

    public interface ProductEndpoint {
        String BASE_URL = "/product";
        String GET_ALL_PRODUCTS = "";
        String GET_BY_ID = "/{id}";
    }

    public interface UrlAuthorization {
        String BASE_URL = "/urlsecurity";
        String USER_PAGE = "/userpage";
        String ADMIN_PAGE = "/adminpage";
        String COMMON_PAGE = "/commonpage";
    }

    public interface MethodAuthorization {
        String BASE_URL = "/methodsecurity";
        String USER_PAGE = "/userpage";
        String ADMIN_PAGE = "/adminpage";
        String COMMON_PAGE = "/commonpage";
    }
}
