package az.baxtiyargil.kafkastreamsdemo.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import static az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.SERIAL_VERSION_UID;

@Data
@Embeddable
public class OrderItemId implements Serializable {

    @Serial
    private static final long serialVersionUID = SERIAL_VERSION_UID;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_item_id", unique = true, nullable = false, updatable = false)
    private Long lineItemId;
}
