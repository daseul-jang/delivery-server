package com.study.delivery.domain.restaurant.application;

import com.study.delivery.domain.order.order.dto.response.OrderResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;

public interface RestaurantService {
    Slice<OrderResponse> getRestaurantOrders(Long restaurantId, LocalDate start, LocalDate end, Long lastId, Pageable pageable);
}
