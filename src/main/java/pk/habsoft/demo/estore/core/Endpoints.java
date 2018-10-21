package pk.habsoft.demo.estore.core;

public interface Endpoints {

    String CONTEXT_PATH = "/mma";

    public interface Test {
        String BASE_URL = "/test";
    }

    public interface Auth {

        String BASE_URI = "/auth";

        String LOGIN = "/login";
        String REFRESH = "/refresh";
    }

    /*
     * Common APIs
     */
    public interface Common {
        String BASE_URI = "/common";
        String GET_USER_ROLES = "/userroles";
        String GET_SERVER_TIME = "/servertime";
        String PING_SERVER = "/ping";
    }

    public interface User {
        String BASE_URI = "/user";
        String GET_USER_BY_ID = "/{userId}";
        String CREATE_USER = "";
        String UPDATE_USER = "/{userId}";
        String DELETE_USER_BY_ID = "/{userId}";
        String CHANGE_PASSWORD = "/changepassword";

        String RESET_PASSWORD_PATH_PARAM = "token";
    }

    public interface ProductEndpoint {
        String BASE_URI = "/product";
        String GET_ALL_PRODUCTS = "";
        String GET_BY_ID = "/{id}";
    }

    @Deprecated
    public interface UrlAuthorization {
        String BASE_URL = "/urlsecurity";
        String USER_PAGE = "/userpage";
        String ADMIN_PAGE = "/adminpage";
        String COMMON_PAGE = "/commonpage";
    }

    @Deprecated
    public interface MethodAuthorization {
        String BASE_URL = "/methodsecurity";
        String USER_PAGE = "/userpage";
        String ADMIN_PAGE = "/adminpage";
        String COMMON_PAGE = "/commonpage";
    }
}
