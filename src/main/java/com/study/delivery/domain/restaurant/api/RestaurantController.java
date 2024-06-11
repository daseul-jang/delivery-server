package com.study.delivery.domain.restaurant.api;

import com.study.delivery.domain.order.order.dto.response.OrderResponse;
import com.study.delivery.domain.restaurant.application.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("{id}/orders")
    public ResponseEntity<List<OrderResponse>> getOrder(@PathVariable("id") Long restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantOrders(restaurantId));
    }
}
