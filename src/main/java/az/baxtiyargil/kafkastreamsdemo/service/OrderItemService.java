package az.baxtiyargil.kafkastreamsdemo.service;

import az.baxtiyargil.kafkastreamsdemo.domain.OrderItem;
import az.baxtiyargil.kafkastreamsdemo.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemRepository.findByIdOrderId(orderId);
    }
}
