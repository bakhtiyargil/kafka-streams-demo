package az.baxtiyargil.kafkastreamsdemo.messaging.component;

import az.baxtiyargil.kafkastreamsdemo.messaging.MessageProducer;
import az.baxtiyargil.kafkastreamsdemo.messaging.TracingEventConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsumerErrorWrapper {

    private final RetryDecider retryDecider;
    private final MessageProducer messageProducer;

    public <T> TracingEventConsumer<T> wrap(TracingEventConsumer<T> tracingEventConsumer) {
        return message -> {
            try {
                tracingEventConsumer.accept(message);
            } catch (Exception ex) {
                if (message instanceof Message<?> msg) {
                    if (retryDecider.isRetryable(ex, msg)) {
                        messageProducer.sendRetryEvent(msg, ex);
                    } else {
                        throw ex;
                    }
                }
            }
        };
    }
}
