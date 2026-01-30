package az.baxtiyargil.kafkastreamsdemo.messaging.event;

import az.baxtiyargil.kafkastreamsdemo.domain.entity.Order;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;
import static az.baxtiyargil.kafkastreamsdemo.messaging.event.EventType.ORDER_CREATED_EVENT;

public class OrderCreatedEvent implements Event<OrderCreatedEvent.Payload> {

    private final UUID eventId;
    private final EventType eventType;
    private final String aggregateId;
    private final LocalDateTime occurredAt;
    private final LocalDateTime ttl;
    private final String traceId;

    @Getter
    private final Payload payload;

    public OrderCreatedEvent(String traceId, UUID eventId, Payload payload) {
        this.eventId = eventId;
        this.eventType = ORDER_CREATED_EVENT;
        this.aggregateId = String.valueOf(payload.orderId);
        this.occurredAt = LocalDateTime.now();
        this.ttl = LocalDateTime.now().plusMinutes(1);
        this.traceId = traceId;
        this.payload = payload;
    }

    public static OrderCreatedEvent from(String traceId, UUID eventId, Order order) {
        return new OrderCreatedEvent(traceId, eventId, Payload.from(order));
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public String getType() {
        return eventType.name();
    }

    @Override
    public String getAggregateId() {
        return aggregateId;
    }

    @Override
    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    @Override
    public LocalDateTime getTtl() {
        return ttl;
    }

    @Override
    public String getTraceId() {
        return traceId;
    }

    public record Payload(Long orderId,
                          LocalDateTime orderedAt,
                          Long customerId,
                          String status,
                          Long storeId) {
        public static Payload from(Order order) {
            return new Payload(
                    order.getId(),
                    order.getOrderedAt(),
                    order.getCustomerId(),
                    order.getStatus().name(),
                    order.getStoreId());
        }
    }
}
