package az.baxtiyargil.kafkastreamsdemo.service;

import az.baxtiyargil.kafkastreamsdemo.domain.entity.Inventory;
import az.baxtiyargil.kafkastreamsdemo.domain.entity.Order;
import az.baxtiyargil.kafkastreamsdemo.error.ApplicationException;
import az.baxtiyargil.kafkastreamsdemo.error.ErrorCode;
import az.baxtiyargil.kafkastreamsdemo.mapper.OrderMapper;
import az.baxtiyargil.kafkastreamsdemo.messaging.MessageProducer;
import az.baxtiyargil.kafkastreamsdemo.messaging.event.OrderCreatedEvent;
import az.baxtiyargil.kafkastreamsdemo.model.CreateOrderRequest;
import az.baxtiyargil.kafkastreamsdemo.repository.InventoryRepository;
import az.baxtiyargil.kafkastreamsdemo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.UUID;

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
        messageProducer.sendOrderEvent(new OrderCreatedEvent(UUID.randomUUID(), order.getId()),
                String.valueOf(order.getId()));
    }

    @Transactional
    public void updateInventory(Long id) {
        var order = findById(id);

        var storeId = order.getStoreId();
        var productId = order.getOrderItems().get(0).getProductId();
        var productQuantity = order.getOrderItems().get(0).getQuantity();
        var inventory = findInventoryByStoreAndProductId(storeId, productId);

        inventory.updateIfAvailableInventory(productQuantity);
        inventoryRepository.save(inventory);
    }

    private Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCode.ORDER_NOT_FOUND, Map.of("id", id)));
    }

    private Inventory findInventoryByStoreAndProductId(Long storeId, Long productId) {
        return inventoryRepository.findByStoreIdAndProductId(storeId, productId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorCode.INVENTORY_NOT_FOUND, Map.of("storeId", storeId, "productId", productId)));
    }
}
