package az.baxtiyargil.kafkastreamsdemo.messaging;

import az.baxtiyargil.kafkastreamsdemo.configuration.TraceContext;
import org.springframework.messaging.Message;
import java.util.function.Consumer;
import static az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.Messaging.HEADER_X_TRACE_ID;

public interface EventConsumer<T> extends Consumer<T> {

    void doAccept(T t);

    @Override
    default void accept(T t) {
        if (t instanceof Message<?> message) {
            var traceId = message.getHeaders().get(HEADER_X_TRACE_ID);
            TraceContext.setTraceId(String.valueOf(traceId));
        }
        doAccept(t);
    }

}
