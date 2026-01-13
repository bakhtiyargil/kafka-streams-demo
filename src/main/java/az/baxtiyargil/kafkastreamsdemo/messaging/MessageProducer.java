package az.baxtiyargil.kafkastreamsdemo.messaging;

import az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.Messaging;
import az.baxtiyargil.kafkastreamsdemo.messaging.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProducer {

    private final StreamBridge streamBridge;

    public <T extends DomainEvent<?>> void sendOrderEvent(T event) {
        log.info("Sending eventType: {}, payload: {} ", event.getType(), event);
        Message<?> message = MessageBuilder.withPayload(event)
                .setHeader(Messaging.HEADER_X_EVENT_TYPE, event.getType())
                .setHeader(KafkaHeaders.KEY, event.getEventId())
                .build();
        streamBridge.send(Messaging.OutputChannel.ORDER, message);
    }

    public <T extends DomainEvent<?>> void sendPaymentMessage(T event) {
        log.info("Sending eventType: {}, payload: {} ", event.getType(), event);
        Message<?> message = MessageBuilder.withPayload(event)
                .setHeader(Messaging.HEADER_X_EVENT_TYPE, event.getType())
                .setHeader(KafkaHeaders.KEY, event.getEventId())
                .build();
        streamBridge.send(Messaging.OutputChannel.PAYMENT, message);
    }

}
