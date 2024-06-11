package com.study.delivery.domain.restaurant.application;

import com.study.delivery.domain.order.order.dao.OrderMenuRepository;
import com.study.delivery.domain.order.order.dao.OrderRepository;
import com.study.delivery.domain.order.order.dto.response.OrderMenuResponse;
import com.study.delivery.domain.order.order.dto.response.OrderResponse;
import com.study.delivery.domain.order.order.entity.OrderMenu;
import com.study.delivery.domain.restaurant.dao.RestaurantRepository;
import com.study.delivery.domain.restaurant.entity.Restaurant;
import com.study.delivery.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;

    @Override
    public List<OrderResponse> getRestaurantOrders(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "존재하지 않는 가게입니다."));

        return orderRepository.findAllByRestaurant(restaurant).stream()
                .map(order -> {
                    List<OrderMenu> orderMenus = orderMenuRepository.findAllByOrder(order);
                    List<OrderMenuResponse> orderMenuResponses =
                            orderMenus.stream().map(OrderMenuResponse::of).toList();

                    return OrderResponse.of(order, orderMenuResponses);
                }).collect(Collectors.toList());
    }
}
