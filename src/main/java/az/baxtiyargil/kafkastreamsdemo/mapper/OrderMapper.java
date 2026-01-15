package az.baxtiyargil.kafkastreamsdemo.mapper;

import az.baxtiyargil.kafkastreamsdemo.domain.entity.Order;
import az.baxtiyargil.kafkastreamsdemo.domain.entity.OrderItem;
import az.baxtiyargil.kafkastreamsdemo.domain.entity.OrderItemId;
import az.baxtiyargil.kafkastreamsdemo.model.AddOrderItemRequest;
import az.baxtiyargil.kafkastreamsdemo.model.CreateOrderRequest;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class OrderMapper {

    public Order toOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setStoreId(request.getStoreId());
        order.setCustomerId(request.getCustomerId());
        List<OrderItem> orderItems = IntStream
                .range(0, request.getOrderItems().size())
                .mapToObj(i -> toOrderItem(
                        request.getOrderItems().get(i),
                        order,
                        i + 1
                ))
                .toList();
        order.setOrderItems(orderItems);
        return order;
    }

    public OrderItem toOrderItem(AddOrderItemRequest request, Order order, int itemId) {
        OrderItemId orderItemId = new OrderItemId();
        orderItemId.setLineItemId(itemId);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemId);
        orderItem.setQuantity(request.getQuantity());
        orderItem.setProductId(request.getProductId());
        orderItem.setUnitPrice(request.getUnitPrice());
        orderItem.setOrder(order);
        return orderItem;
    }

}
