package az.baxtiyargil.kafkastreamsdemo.messaging.event;

import az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.Messaging.ConsumerFunctionNames;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType {

    ORDER_CREATED_EVENT(ConsumerFunctionNames.ORDER_CREATED_EVENT_CONSUMER);

    private final String consumerFunctionName;

}
