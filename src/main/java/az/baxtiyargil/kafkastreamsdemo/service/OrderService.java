package az.baxtiyargil.kafkastreamsdemo.service;

import az.baxtiyargil.kafkastreamsdemo.mapper.OrderMapper;
import az.baxtiyargil.kafkastreamsdemo.messaging.MessageProducer;
import az.baxtiyargil.kafkastreamsdemo.messaging.event.EventType;
import az.baxtiyargil.kafkastreamsdemo.messaging.event.OrderCreatedEvent;
import az.baxtiyargil.kafkastreamsdemo.model.CreateOrderRequest;
import az.baxtiyargil.kafkastreamsdemo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final MessageProducer messageProducer;

    @Transactional
    public void create(CreateOrderRequest request) {
        var order = orderMapper.toOrder(request);
        orderRepository.save(order);
        messageProducer.sendOrderEvent(new OrderCreatedEvent(order.getId()), String.valueOf(order.getId()));
    }

}
