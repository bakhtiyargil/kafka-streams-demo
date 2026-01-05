package az.baxtiyargil.kafkastreamsdemo.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ErrorResponse {

    private final String id;
    private final String code;
    private final String message;
    private final Integer status;
    private final LocalDateTime timestamp;
    private final List<ValidationError> validationErrors;

    public ErrorResponse(String id, String code, String message, Integer status) {
        this.id = id;
        this.code = code;
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.validationErrors = new ArrayList<>();
    }

    public void addValidationError(String property, String message) {
        this.validationErrors.add(new ValidationError(property, message));
    }

    public record ValidationError(String property, String message) {
    }
}
