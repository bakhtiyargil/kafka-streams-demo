package az.baxtiyargil.kafkastreamsdemo.messaging.component;

import az.baxtiyargil.kafkastreamsdemo.configuration.tracing.TraceContext;
import az.baxtiyargil.kafkastreamsdemo.utility.MessagingUtility;
import org.springframework.cloud.function.context.catalog.FunctionAroundWrapper;
import org.springframework.cloud.function.context.catalog.SimpleFunctionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

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
                    traceId = MessagingUtility.extractTraceId(message);
                    TraceContext.clearTraceId();
                    TraceContext.setTraceId(traceId);
                } else {
                    message = MessagingUtility.toMessage(input);
                }

                message = MessagingUtility.withTraceId(message, traceId);
                return targetFunction.apply(message);
            }
        };
    }
}