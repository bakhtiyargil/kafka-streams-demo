package az.baxtiyargil.kafkastreamsdemo.messaging.component;

import az.baxtiyargil.kafkastreamsdemo.configuration.TraceContext;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import static az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.Messaging.HEADER_X_TRACE_ID;

@Component
@GlobalChannelInterceptor
public class TracingInterceptor implements ChannelInterceptor {

    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        String traceId = TraceContext.popTraceId();
        return MessageBuilder.fromMessage(message)
                .setHeader(HEADER_X_TRACE_ID, traceId)
                .build();
    }

    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        var traceId = message.getHeaders().get(HEADER_X_TRACE_ID, String.class);
        TraceContext.setTraceId(traceId);
        return message;
    }
}
