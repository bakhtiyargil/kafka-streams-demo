package az.baxtiyargil.kafkastreamsdemo.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Data
@Embeddable
public class OrderItemId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    private Long orderId;

    @NotNull
    private Long lineItemId;
}
