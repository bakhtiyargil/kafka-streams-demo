package az.baxtiyargil.kafkastreamsdemo.error;

import lombok.Getter;

public enum ErrorCode {

    ORDER_NOT_FOUND("Order not found with ID {id}"),
    INVENTORY_NOT_FOUND("Inventory not found with store ID {storeId} and product ID {productId}");

    @Getter
    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
