package az.baxtiyargil.kafkastreamsdemo.messaging.event;

import java.util.UUID;

public interface Event {

    EventType getType();
    UUID getCorrelationId();

}
