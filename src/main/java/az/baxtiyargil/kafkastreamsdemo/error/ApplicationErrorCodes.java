package az.baxtiyargil.kafkastreamsdemo.error;

import org.springframework.http.HttpStatus;

public enum ApplicationErrorCodes implements ErrorCode {

    ORDER_NOT_FOUND("ORDER_NOT_FOUND"),
    INVENTORY_NOT_FOUND("INVENTORY_NOT_FOUND"),
    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND"),
    INSUFFICIENT_INVENTORY("INSUFFICIENT_INVENTORY");

    private final String message;
    private final HttpStatus status;

    ApplicationErrorCodes(String message) {
        this.message = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
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
