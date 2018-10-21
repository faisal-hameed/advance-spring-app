package pk.habsoft.demo.estore.exceptions;

public class ValidationFailedException extends ECoreException {

    private static final long serialVersionUID = 1L;

    public ValidationFailedException(String message) {
        super(message);
    }

    public ValidationFailedException(String message, Throwable exception) {
        super(message, exception);
    }

}
