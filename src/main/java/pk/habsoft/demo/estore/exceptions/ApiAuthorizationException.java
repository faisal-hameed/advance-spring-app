package pk.habsoft.demo.estore.exceptions;

public class ApiAuthorizationException extends ECoreException {

    private static final long serialVersionUID = 1L;

    public ApiAuthorizationException(String message) {
        super(message);
    }

    public ApiAuthorizationException(String message, Throwable exception) {
        super(message, exception);
    }

}
