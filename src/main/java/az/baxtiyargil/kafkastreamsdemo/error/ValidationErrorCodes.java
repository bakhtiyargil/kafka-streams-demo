package az.baxtiyargil.kafkastreamsdemo.error;

import org.springframework.http.HttpStatus;

public enum ValidationErrorCodes implements ErrorCode {

    ORDER_ITEMS_SIZE_EXCEEDED("ORDER_ITEMS_SIZE_EXCEEDED");

    private final String message;
    private final HttpStatus status;

    ValidationErrorCodes(String message) {
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    @Override
    public HttpStatus status() {
        return status;
    }

    @Override
    public String message() {
        return message;
    }
}
