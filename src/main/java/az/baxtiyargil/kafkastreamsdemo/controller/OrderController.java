package az.baxtiyargil.kafkastreamsdemo.controller;

import az.baxtiyargil.kafkastreamsdemo.model.CreateOrderRequest;
import az.baxtiyargil.kafkastreamsdemo.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void create(@RequestBody @Valid CreateOrderRequest request) {
        orderService.create(request);
    }
}
