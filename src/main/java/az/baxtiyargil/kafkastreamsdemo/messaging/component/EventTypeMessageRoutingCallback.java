package az.baxtiyargil.kafkastreamsdemo.messaging.component;

import az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.Messaging;
import az.baxtiyargil.kafkastreamsdemo.messaging.event.EventType;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.messaging.Message;

public class EventTypeMessageRoutingCallback implements MessageRoutingCallback {

    @Override
    public String routingResult(Message<?> message) {
        var eventType = EventType.valueOf((String) message.getHeaders().get(Messaging.HEADER_X_EVENT_TYPE));
        return eventType.getConsumerFunctionName();
    }

}
