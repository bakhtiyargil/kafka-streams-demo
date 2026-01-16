package az.baxtiyargil.kafkastreamsdemo.messaging.component;

import az.baxtiyargil.kafkastreamsdemo.configuration.tracing.TraceContext;
import az.baxtiyargil.kafkastreamsdemo.messaging.event.DomainEvent;
import org.springframework.cloud.function.context.catalog.FunctionAroundWrapper;
import org.springframework.cloud.function.context.catalog.SimpleFunctionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import static az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.Messaging.HEADER_X_TRACE_ID;

@Configuration
public class TracingFunctionWrapper {

    @Bean
    public FunctionAroundWrapper tracingAroundWrapper() {
        return new FunctionAroundWrapper() {
            @Override
            public Object doApply(Object input, SimpleFunctionRegistry.FunctionInvocationWrapper targetFunction) {
                String traceId = null;
                Message<?> message;
                if (input instanceof Message<?> msg) {
                    message = msg;
                    traceId = msg.getHeaders().get(HEADER_X_TRACE_ID, String.class);

                    if (traceId == null && msg.getPayload() instanceof DomainEvent event) {
                        traceId = event.getTraceId();
                    }

                    if (traceId == null) {
                        traceId = TraceContext.getTraceId();
                    } else {
                        TraceContext.clearTraceId();
                        TraceContext.setTraceId(traceId);
                    }
                } else {
                    message = MessageBuilder.withPayload(input).build();
                }

                message = MessageBuilder.fromMessage(message).setHeader(HEADER_X_TRACE_ID, traceId).build();
                return targetFunction.apply(message);
            }
        };
    }
}