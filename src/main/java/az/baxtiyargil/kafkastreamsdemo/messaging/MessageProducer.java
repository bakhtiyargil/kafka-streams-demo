package az.baxtiyargil.kafkastreamsdemo.messaging;

import az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.Messaging;
import az.baxtiyargil.kafkastreamsdemo.messaging.event.DomainEvent;
import az.baxtiyargil.kafkastreamsdemo.messaging.event.EventType;
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

    public <T extends DomainEvent> void sendOrderEvent(T event) {
        log.info("Sending event: {}, payload: {} ", event.getType(), event);
        Message<T> message = MessageBuilder.withPayload(event)
                .setHeader(Messaging.HEADER_X_EVENT_TYPE, event.getType())
                .setHeader(KafkaHeaders.KEY, event.getEventId().toString())
                .build();
        streamBridge.send(Messaging.OutputChannel.ORDER, message);
    }

    public <T extends DomainEvent> void sendRetryEvent(Message<?> message, Throwable throwable) {
        int retryCount = (Integer) message.getHeaders().getOrDefault(Messaging.HEADER_X_RETRY_COUNT, 0);
        var messageKey = message.getHeaders().getOrDefault(KafkaHeaders.RECEIVED_KEY, "");

        String outputChannel = null;
        if (message.getPayload() instanceof DomainEvent event) {
            var eventType = EventType.of(event.getType());
            outputChannel = eventType.getOutputChannelName();
        }
        Message<?> retryMessage = MessageBuilder.fromMessage(message)
                .setHeader(Messaging.HEADER_X_RETRY_COUNT, ++retryCount)
                .setHeader(Messaging.HEADER_X_RETRY_REASON, throwable.getClass().getSimpleName())
                .setHeader(KafkaHeaders.KEY, messageKey)
                .build();
        streamBridge.send(outputChannel, retryMessage);
    }
}
