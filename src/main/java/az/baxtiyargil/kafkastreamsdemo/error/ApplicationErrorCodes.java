package az.baxtiyargil.kafkastreamsdemo.error;

import org.springframework.http.HttpStatus;

public enum ApplicationErrorCodes implements ErrorCode {

    ORDER_NOT_FOUND("Order not found with ID {id}"),
    INVENTORY_NOT_FOUND("Inventory not found with store {storeId} and product {productId}"),
    INSUFFICIENT_INVENTORY("Not enough inventory for product {productId} in store {storeId}");

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
