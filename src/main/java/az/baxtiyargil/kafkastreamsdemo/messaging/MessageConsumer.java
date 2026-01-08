package az.baxtiyargil.kafkastreamsdemo.messaging;

import az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.Messaging.ConsumerFunctionNames;
import az.baxtiyargil.kafkastreamsdemo.messaging.event.OrderCreatedEvent;
import az.baxtiyargil.kafkastreamsdemo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageConsumer {

    private static final String LOG_FORMAT = "Received event: {}, payload: {}";
    private final OrderService orderService;

    @Bean(ConsumerFunctionNames.ORDER_CREATED_EVENT_CONSUMER)
    public Consumer<Message<OrderCreatedEvent>> onOrderCreatedEvent() {
        return message -> {
            log.info(LOG_FORMAT, message.getPayload().getType(), message.getPayload());
            orderService.updateInventory(message.getPayload().orderId());
        };
    }

}
