package com.study.delivery.domain.restaurant.application;

import com.study.delivery.domain.order.order.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface RestaurantService {
    Page<OrderResponse> getRestaurantOrders(Long restaurantId, LocalDate start, LocalDate end, Pageable pageable);
}
