package az.baxtiyargil.kafkastreamsdemo.service;

import az.baxtiyargil.kafkastreamsdemo.configuration.TraceContext;
import az.baxtiyargil.kafkastreamsdemo.domain.entity.Inventory;
import az.baxtiyargil.kafkastreamsdemo.domain.entity.Order;
import az.baxtiyargil.kafkastreamsdemo.domain.entity.OrderItem;
import az.baxtiyargil.kafkastreamsdemo.error.ApplicationErrorCodes;
import az.baxtiyargil.kafkastreamsdemo.error.exception.ApplicationException;
import az.baxtiyargil.kafkastreamsdemo.mapper.OrderMapper;
import az.baxtiyargil.kafkastreamsdemo.messaging.MessageProducer;
import az.baxtiyargil.kafkastreamsdemo.messaging.event.OrderCreatedEvent;
import az.baxtiyargil.kafkastreamsdemo.model.CreateOrderRequest;
import az.baxtiyargil.kafkastreamsdemo.repository.InventoryRepository;
import az.baxtiyargil.kafkastreamsdemo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final MessageProducer messageProducer;
    private final InventoryRepository inventoryRepository;

    @Transactional
    public void create(CreateOrderRequest request) {
        var order = orderMapper.toOrder(request);
        order.validate();
        checkProduct(order);

        orderRepository.save(order);
        messageProducer.sendOrderEvent(new OrderCreatedEvent(order, TraceContext.getTraceId()));
    }

    @Transactional
    public void updateInventory(Long id) {
        var order = findById(id);
        var storeId = order.getStoreId();

        for (OrderItem orderItem : order.getOrderItems()) {
            var inventory = findInventoryByStoreAndProductId(storeId, orderItem.getProductId());
            inventory.updateIfAvailableInventory(orderItem.getQuantity());
            inventoryRepository.save(inventory);
        }
    }

    private void checkProduct(Order order) {
        Set<Long> itemIds = order.getOrderItems()
                .stream()
                .map(OrderItem::getProductId)
                .collect(Collectors.toSet());
        Set<Long> existingIds = productService.findExistingProductIds(itemIds);
        itemIds.removeAll(existingIds);

        if (!itemIds.isEmpty()) {
            throw new ApplicationException(ApplicationErrorCodes.PRODUCT_NOT_FOUND, itemIds);
        }
    }

    private Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ApplicationErrorCodes.ORDER_NOT_FOUND, id));
    }

    private Inventory findInventoryByStoreAndProductId(Long storeId, Long productId) {
        return inventoryRepository.findByStoreIdAndProductId(storeId, productId)
                .orElseThrow(() -> new ApplicationException(
                        ApplicationErrorCodes.INVENTORY_NOT_FOUND, storeId, productId));
    }
}
