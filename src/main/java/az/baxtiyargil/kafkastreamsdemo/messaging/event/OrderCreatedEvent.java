package az.baxtiyargil.kafkastreamsdemo.messaging.event;

import java.util.UUID;

public record OrderCreatedEvent(UUID correlationId, Long orderId) implements Event {

    @Override
    public EventType getType() {
        return EventType.ORDER_CREATED_EVENT;
    }

    @Override
    public UUID getCorrelationId() {
        return correlationId;
    }

}
