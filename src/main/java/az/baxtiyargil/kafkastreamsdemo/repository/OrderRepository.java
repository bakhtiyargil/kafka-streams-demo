package az.baxtiyargil.kafkastreamsdemo.repository;

import az.baxtiyargil.kafkastreamsdemo.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
