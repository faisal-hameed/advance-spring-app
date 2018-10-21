package pk.habsoft.demo.estore.core;

public class EResponse<T> {

    private boolean success;
    private String message;
    private T data;

    public EResponse() {
        // Default constructor
    }

    public EResponse(boolean success, String message) {
        this(success, message, null);
    }

    public EResponse(boolean success, T data) {
        this(success, null, data);
    }

    public EResponse(boolean success, String message, T body) {
        super();
        this.success = success;
        this.message = message;
        this.data = body;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EResponse [success=" + success + ", message=" + message + ", data=" + data + "]";
    }

}
