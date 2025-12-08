package az.baxtiyargil.kafkastreamsdemo.messaging.event;

import az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.Messaging.ConsumerFunctionNames;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum EventType {

    FEE_ITEM_RECEIVED_EVENT(ConsumerFunctionNames.FEE_ITEM_RECEIVED_EVENT_CONSUMER),
    FEE_ITEM_CREATED_EVENT(ConsumerFunctionNames.FEE_ITEM_CREATED_EVENT_CONSUMER),
    FEE_ITEM_VALIDATED_EVENT(ConsumerFunctionNames.FEE_ITEM_VALIDATED_EVENT_CONSUMER),
    PAYMENT_CREATED_EVENT(ConsumerFunctionNames.PAYMENT_CREATED_EVENT_CONSUMER);

    private final String consumerFunctionName;

}
