package az.baxtiyargil.kafkastreamsdemo.messaging.event;

import az.baxtiyargil.kafkastreamsdemo.domain.entity.Order;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class OrderCreatedEvent extends DomainEvent {

    private final Payload payload;

    public OrderCreatedEvent(Order order, String traceId) {
        super(
                EventType.ORDER_CREATED_EVENT.name(),
                String.valueOf(order.getId()),
                LocalDateTime.now().plusMinutes(1),
                traceId
        );
        this.payload = new Payload(
                order.getStoreId(),
                order.getOrderedAt(),
                order.getCustomerId(),
                order.getStatus().name(),
                order.getStoreId()
        );
    }

    public record Payload(Long orderId,
                          LocalDateTime orderedAt,
                          Long customerId,
                          String status,
                          Long storeId) {
    }

}
