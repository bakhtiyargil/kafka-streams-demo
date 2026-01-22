package az.baxtiyargil.kafkastreamsdemo.messaging.event;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Event<T> {

    UUID getEventId();

    String getType();

    String getAggregateId();

    LocalDateTime getOccurredAt();

    LocalDateTime getTtl();

    String getTraceId();

    T getPayload();

}
