package com.study.delivery.domain.order.order.api;

import com.study.delivery.domain.order.order.application.OrderService;
import com.study.delivery.domain.order.order.dto.request.OrderRequest;
import com.study.delivery.domain.order.order.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.createOrder(orderRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderResponse.getId())
                .toUri();

        return ResponseEntity.created(location).body(orderResponse);
    }

    @GetMapping("{restaurantId}")
    public ResponseEntity<List<OrderResponse>> getOrder(@PathVariable("restaurantId") Long restaurantId) {
        return ResponseEntity.ok(orderService.getOrders(restaurantId));
    }
}
