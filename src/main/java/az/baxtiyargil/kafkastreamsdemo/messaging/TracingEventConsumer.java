package az.baxtiyargil.kafkastreamsdemo.messaging;

import az.baxtiyargil.kafkastreamsdemo.configuration.tracing.TraceContext;
import az.baxtiyargil.kafkastreamsdemo.messaging.event.DomainEvent;
import org.springframework.messaging.Message;
import java.util.function.Consumer;

public interface TracingEventConsumer<T> extends Consumer<T> {

    void doAccept(T t);

    @Override
    default void accept(T t) {
        if (t instanceof Message<?> message) {
            if (message.getPayload() instanceof DomainEvent event) {
                TraceContext.clearTraceId();
                var traceId = event.getTraceId();
                TraceContext.setTraceId(traceId);
            }
        }
        doAccept(t);
    }

}
