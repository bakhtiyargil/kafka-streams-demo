package az.baxtiyargil.kafkastreamsdemo.domain.event;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class OrderPlaced implements DomainEvent {

    private final String traceId;
    private final String orderId;
    private final UUID eventId = UUID.randomUUID();
    private final LocalDateTime occurredAt = LocalDateTime.now();

    public OrderPlaced(String orderId,  String traceId) {
        this.orderId = orderId;
        this.traceId = traceId;
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public String getAggregateId() {
        return orderId;
    }

    @Override
    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    @Override
    public String getTraceId() {
        return traceId;
    }

}
