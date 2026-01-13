package az.baxtiyargil.kafkastreamsdemo.messaging.event;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class DomainEvent {

    private final UUID eventId;
    private final String type;
    private final String aggregateId;
    private final LocalDateTime createdAt;
    private final LocalDateTime ttl;
    private final String traceId;

    public DomainEvent(String type, String aggregateId, LocalDateTime ttl, String traceId) {
        this.eventId = UUID.randomUUID();
        this.type = type;
        this.aggregateId = aggregateId;
        this.createdAt = LocalDateTime.now();
        this.ttl = ttl;
        this.traceId = traceId;
    }
}
