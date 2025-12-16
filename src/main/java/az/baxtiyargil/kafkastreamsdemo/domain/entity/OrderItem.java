package az.baxtiyargil.kafkastreamsdemo.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.hibernate.Hibernate;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import static az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.SERIAL_VERSION_UID;

@Data
@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItem implements Serializable {

    @Serial
    private static final long serialVersionUID = SERIAL_VERSION_UID;

    @NotNull
    @EmbeddedId
    private OrderItemId id;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @NotNull
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "shipment_id")
    private Long shipmentId;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        OrderItem orderItem = (OrderItem) o;
        return getId() != null && Objects.equals(getId(), orderItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
