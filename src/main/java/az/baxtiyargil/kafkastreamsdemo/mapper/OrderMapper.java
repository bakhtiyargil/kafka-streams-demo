package az.baxtiyargil.kafkastreamsdemo.mapper;

import az.baxtiyargil.kafkastreamsdemo.domain.entity.Order;
import az.baxtiyargil.kafkastreamsdemo.domain.entity.OrderItem;
import az.baxtiyargil.kafkastreamsdemo.model.AddOrderItemRequest;
import az.baxtiyargil.kafkastreamsdemo.model.CreateOrderRequest;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class OrderMapper {

    public Order toOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setStoreId(request.getStoreId());
        order.setCustomerId(request.getCustomerId());

        List<OrderItem> orderItems = request.getOrderItems().stream()
                .map(item -> toOrderItem(item, order))
                .toList();
        order.setOrderItems(orderItems);
        return order;
    }

    public OrderItem toOrderItem(AddOrderItemRequest request, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(request.getQuantity());
        orderItem.setProductId(request.getProductId());
        orderItem.setUnitPrice(request.getUnitPrice());
        orderItem.setOrder(order);
        return orderItem;
    }

}
