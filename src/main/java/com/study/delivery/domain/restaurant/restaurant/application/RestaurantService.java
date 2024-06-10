package com.study.delivery.domain.restaurant.restaurant.application;

import com.study.delivery.domain.order.order.dto.response.OrderResponse;

import java.util.List;

public interface RestaurantService {
    List<OrderResponse> getRestaurantOrders(Long restaurantId);
}
