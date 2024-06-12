package com.study.delivery.domain.restaurant.api;

import com.study.delivery.domain.order.order.dto.response.OrderResponse;
import com.study.delivery.domain.restaurant.application.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("{id}/orders")
    public ResponseEntity<Page<OrderResponse>> getOrders(
            @PathVariable("id") Long restaurantId,
            @RequestParam(name = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            Pageable pageable) {
        return ResponseEntity.ok(restaurantService.getRestaurantOrders(restaurantId, start, end, pageable));
    }
}
