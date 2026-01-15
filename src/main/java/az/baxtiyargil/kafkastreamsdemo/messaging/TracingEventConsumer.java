package az.baxtiyargil.kafkastreamsdemo.messaging;

import az.baxtiyargil.kafkastreamsdemo.configuration.tracing.TraceContext;
import az.baxtiyargil.kafkastreamsdemo.utility.MessagingUtility;
import org.springframework.messaging.Message;
import java.util.function.Consumer;

public interface TracingEventConsumer<T> extends Consumer<T> {

    void doAccept(T t);

    @Override
    default void accept(T t) {
        if (t instanceof Message<?> message) {
            TraceContext.clearTraceId();
            var traceId = MessagingUtility.extractTraceId(message);
            TraceContext.setTraceId(traceId);
        }
        doAccept(t);
    }

}
