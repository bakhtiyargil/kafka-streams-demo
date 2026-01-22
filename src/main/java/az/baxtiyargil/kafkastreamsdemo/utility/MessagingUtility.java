package az.baxtiyargil.kafkastreamsdemo.utility;

import az.baxtiyargil.kafkastreamsdemo.configuration.tracing.TraceContext;
import az.baxtiyargil.kafkastreamsdemo.messaging.event.Event;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import static az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.Messaging.HEADER_X_TRACE_ID;

public final class MessagingUtility {

    private MessagingUtility() {
    }

    public static Message<?> toMessage(Object input) {
        if (input instanceof Message<?> msg) {
            return msg;
        }
        return MessageBuilder.withPayload(input).build();
    }

    public static String extractTraceId(Message<?> message) {
        String traceId = message.getHeaders().get(HEADER_X_TRACE_ID, String.class);

        if (traceId == null && message.getPayload() instanceof Event event) {
            traceId = event.getTraceId();
            return traceId;
        }

        if (traceId == null) {
            traceId = TraceContext.getTraceId();
        }
        return traceId;
    }

    public static Message<?> withTraceId(Message<?> message, String traceId) {
        return MessageBuilder.fromMessage(message)
                .setHeader(HEADER_X_TRACE_ID, traceId)
                .build();
    }
}
