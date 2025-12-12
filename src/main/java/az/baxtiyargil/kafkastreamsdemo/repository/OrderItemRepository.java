package az.baxtiyargil.kafkastreamsdemo.repository;

import az.baxtiyargil.kafkastreamsdemo.domain.entity.OrderItem;
import az.baxtiyargil.kafkastreamsdemo.domain.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}
