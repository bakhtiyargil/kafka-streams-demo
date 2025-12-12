package az.baxtiyargil.kafkastreamsdemo.domain.entity;

import jakarta.persistence.Column;
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
    @Column(name = "order_id", unique = true, nullable = false, updatable = false)
    private Long orderId;

    @NotNull
    @Column(name = "line_item_id", unique = true, nullable = false, updatable = false)
    private Long lineItemId;
}
