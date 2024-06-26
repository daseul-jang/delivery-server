package com.study.delivery.domain.restaurant.application;

import com.study.delivery.domain.order.order.dao.OrderRepository;
import com.study.delivery.domain.order.order.dto.response.OrderMenuResponse;
import com.study.delivery.domain.order.order.dto.response.OrderResponse;
import com.study.delivery.domain.order.order.entity.Order;
import com.study.delivery.domain.restaurant.dao.RestaurantRepository;
import com.study.delivery.domain.restaurant.entity.Restaurant;
import com.study.delivery.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;

    @Override
    public Slice<OrderResponse> getRestaurantOrders(Long restaurantId,
                                                    LocalDate start,
                                                    LocalDate end,
                                                    Long lastId,
                                                    Pageable pageable) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "존재하지 않는 가게입니다."));

        Slice<Order> sliceOrders = orderRepository.restaurantOrderSearchByPeriod(restaurant, start, end, lastId, pageable);

        return sliceOrders.map(order ->
                OrderResponse.of(order, order.getOrderMenus().stream().map(OrderMenuResponse::of).toList()));
    }
}
