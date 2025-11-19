package az.baxtiyargil.kafkastreamsdemo.repository;

import az.baxtiyargil.kafkastreamsdemo.domain.OrderItem;
import az.baxtiyargil.kafkastreamsdemo.domain.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {

    List<OrderItem> findByIdOrderId(Long orderId);
}
