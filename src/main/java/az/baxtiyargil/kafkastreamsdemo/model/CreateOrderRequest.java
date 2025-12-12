package az.baxtiyargil.kafkastreamsdemo.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class CreateOrderRequest {

    @NotNull
    private Long storeId;

    @NotNull
    private Long customerId;

    @NotNull
    private List<@NotEmpty AddOrderItemRequest> orderItems;

}
