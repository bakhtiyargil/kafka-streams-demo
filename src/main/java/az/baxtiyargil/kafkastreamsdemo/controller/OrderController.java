package az.baxtiyargil.kafkastreamsdemo.controller;

import az.baxtiyargil.kafkastreamsdemo.domain.OrderItem;
import az.baxtiyargil.kafkastreamsdemo.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderItemService orderItemService;

    @GetMapping("/{id}/items")
    public List<OrderItem> findItemsByOrderId(@PathVariable Long id) {
        return orderItemService.findByOrderId(id);
    }
}
