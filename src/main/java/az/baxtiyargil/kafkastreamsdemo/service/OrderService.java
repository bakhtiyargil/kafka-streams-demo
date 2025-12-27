package az.baxtiyargil.kafkastreamsdemo.service;

import az.baxtiyargil.kafkastreamsdemo.domain.entity.Inventory;
import az.baxtiyargil.kafkastreamsdemo.domain.entity.Order;
import az.baxtiyargil.kafkastreamsdemo.mapper.OrderMapper;
import az.baxtiyargil.kafkastreamsdemo.messaging.MessageProducer;
import az.baxtiyargil.kafkastreamsdemo.messaging.event.OrderCreatedEvent;
import az.baxtiyargil.kafkastreamsdemo.model.CreateOrderRequest;
import az.baxtiyargil.kafkastreamsdemo.repository.InventoryRepository;
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
    private final InventoryRepository inventoryRepository;

    @Transactional
    public void create(CreateOrderRequest request) {
        var order = orderMapper.toOrder(request);
        orderRepository.save(order);
        messageProducer.sendOrderEvent(new OrderCreatedEvent(order.getId()), String.valueOf(order.getId()));
    }

    @Transactional
    public void updateInventory(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("order not found"));
        var storeId = order.getStoreId();
        var productId = order.getOrderItems().get(0).getProductId();
        var productQuantity = order.getOrderItems().get(0).getQuantity();
        Inventory inventory = inventoryRepository.findByStoreIdAndProductId(storeId, productId)
                .orElseThrow(() -> new RuntimeException("inventory not found"));
        inventory.updateIfAvailableInventory(productQuantity);
        inventoryRepository.save(inventory);
    }
}
