package az.baxtiyargil.kafkastreamsdemo.model;

import jakarta.validation.Valid;
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

    @NotEmpty
    private List<@NotNull @Valid AddOrderItemRequest> orderItems;

}
