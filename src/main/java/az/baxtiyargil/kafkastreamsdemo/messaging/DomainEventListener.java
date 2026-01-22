package az.baxtiyargil.kafkastreamsdemo.messaging;

import az.baxtiyargil.kafkastreamsdemo.domain.event.OrderPlaced;
import az.baxtiyargil.kafkastreamsdemo.messaging.event.OrderCreatedEvent;
import az.baxtiyargil.kafkastreamsdemo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class DomainEventListener {

    private final MessageProducer messageProducer;
    private final OrderRepository orderRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(OrderPlaced event) {
        var orderOpt = orderRepository.findById(Long.valueOf(event.getAggregateId()));
        orderOpt.ifPresent(order ->
                messageProducer.sendOrderEvent(OrderCreatedEvent.from(event.getTraceId(), event.getEventId(), order))
        );
    }

}
