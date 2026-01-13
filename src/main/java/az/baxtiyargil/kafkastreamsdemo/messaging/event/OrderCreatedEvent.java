package az.baxtiyargil.kafkastreamsdemo.messaging.event;

import az.baxtiyargil.kafkastreamsdemo.domain.entity.Order;
import java.time.LocalDateTime;

public class OrderCreatedEvent extends DomainEvent<Order> {

    public OrderCreatedEvent(Order order, String traceId) {
        super(EventType.ORDER_CREATED_EVENT.name(),
                String.valueOf(order.getId()),
                order,
                LocalDateTime.now().plusMinutes(1),
                traceId);
    }
}
