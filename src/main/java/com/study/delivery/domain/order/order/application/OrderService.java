package com.study.delivery.domain.order.order.application;

import com.study.delivery.domain.order.order.dto.request.OrderRequest;
import com.study.delivery.domain.order.order.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getOrders(Long restaurantId);

    OrderResponse createOrder(OrderRequest orderRequest);
}
