package pk.habsoft.demo.estore.exceptions;

public class ECoreException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ECoreException(String message) {
        super(message);
    }

    public ECoreException(String message, Throwable exception) {
        super(message, exception);
    }

}
