package az.baxtiyargil.kafkastreamsdemo.messaging.event;

import java.time.LocalDateTime;
import java.util.UUID;

public interface DomainEvent {

    UUID getEventId();

    String getType();

    String getAggregateId();

    LocalDateTime getCreatedAt();

    LocalDateTime getTtl();

    String getTraceId();

}
