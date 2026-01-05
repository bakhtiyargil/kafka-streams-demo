package az.baxtiyargil.kafkastreamsdemo.error;

import org.springframework.http.HttpStatus;

public enum ValidationErrorCodes implements ErrorCode {

    ORDER_ITEMS_SIZE_EXCEEDED("ORDER_ITEMS_SIZE_EXCEEDED"),
    VALIDATION_ERROR("Validation failed");

    private final String message;
    private final HttpStatus status;

    ValidationErrorCodes(String message) {
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    @Override
    public String asString() {
        return this.name();
    }

    @Override
    public HttpStatus status() {
        return this.status;
    }

    @Override
    public String message() {
        return this.message;
    }
}
