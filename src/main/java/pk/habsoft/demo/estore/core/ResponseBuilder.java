package pk.habsoft.demo.estore.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder<T> {

    private ResponseBuilder() {
        // Hide Utility class constructor
    }

    public static ResponseEntity<EResponse<Void>> error(String message) {
        EResponse<Void> resp = new EResponse<>(false, message, null);
        return ResponseEntity.ok(resp);
    }

    public static <T> ResponseEntity<EResponse<T>> success(T body) {
        EResponse<T> resp = new EResponse<>(true, "", body);
        return ResponseEntity.ok(resp);
    }

    public static <T> ResponseEntity<EResponse<T>> success(String message) {
        EResponse<T> resp = new EResponse<>(true, message, null);
        return ResponseEntity.ok(resp);
    }

    public static <T> ResponseEntity<T> badRequest(T body) {
        return ResponseEntity.badRequest().body(body);
    }

    public static <T> ResponseEntity<EResponse<T>> unauthorized(String message) {
        EResponse<T> resp = new EResponse<>(false, message, null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resp);
    }

}
