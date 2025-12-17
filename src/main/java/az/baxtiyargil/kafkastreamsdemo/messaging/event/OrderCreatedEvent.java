package az.baxtiyargil.kafkastreamsdemo.messaging.event;

public record OrderCreatedEvent(Long id) implements Event {

    @Override
    public EventType getType() {
        return EventType.ORDER_CREATED_EVENT;
    }

}
