package az.baxtiyargil.kafkastreamsdemo.messaging.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class DomainEvent<T> {

    private UUID eventId;
    private String type;
    private String aggregateId;
    private T payload;
    private LocalDateTime createdAt;
    private LocalDateTime ttl;
    private String traceId;

    public DomainEvent(String type, String aggregateId, T payload, LocalDateTime ttl, String traceId) {
        this.eventId = UUID.randomUUID();
        this.type = type;
        this.aggregateId = aggregateId;
        this.payload = payload;
        this.createdAt = LocalDateTime.now();
        this.ttl = ttl;
        this.traceId = traceId;
    }
}
