package az.baxtiyargil.kafkastreamsdemo.messaging.event;

import az.baxtiyargil.kafkastreamsdemo.domain.entity.Order;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;
import static az.baxtiyargil.kafkastreamsdemo.messaging.event.EventType.ORDER_CREATED_EVENT;

public class OrderCreatedEvent implements DomainEvent {

    private final UUID eventId;
    private final EventType eventType;
    private final String aggregateId;
    private final LocalDateTime createdAt;
    private final LocalDateTime ttl;
    private final String traceId;

    @Getter
    private final Payload payload;

    public OrderCreatedEvent(String traceId, Payload payload) {
        this.eventId = UUID.randomUUID();
        this.eventType = ORDER_CREATED_EVENT;
        this.aggregateId = String.valueOf(payload.orderId);
        this.createdAt = LocalDateTime.now();
        this.ttl = LocalDateTime.now().plusMinutes(1);
        this.traceId = traceId;
        this.payload = payload;
    }

    public static OrderCreatedEvent from(String traceId, Order order) {
        return new OrderCreatedEvent(traceId, Payload.from(order));
    }

    @Override
    public UUID getEventId() {
        return this.eventId;
    }

    @Override
    public String getType() {
        return this.eventType.name();
    }

    @Override
    public String getAggregateId() {
        return this.aggregateId;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public LocalDateTime getTtl() {
        return this.ttl;
    }

    @Override
    public String getTraceId() {
        return this.traceId;
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
