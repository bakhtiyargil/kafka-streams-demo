package az.baxtiyargil.kafkastreamsdemo.messaging.event;

import az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.Messaging.ConsumerFunctionNames;
import az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.Messaging.OutputChannel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType {

    ORDER_CREATED_EVENT(ConsumerFunctionNames.ORDER_CREATED_EVENT_CONSUMER, OutputChannel.ORDER_RETRY);

    private final String consumerFunctionName;
    private final String outputChannelName;

    public static EventType of(String eventType) {
        return EventType.valueOf(eventType);
    }
}
