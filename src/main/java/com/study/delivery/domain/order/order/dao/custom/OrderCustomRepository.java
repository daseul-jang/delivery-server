package com.study.delivery.domain.order.order.dao.custom;

import com.study.delivery.domain.order.order.entity.Order;
import com.study.delivery.domain.restaurant.entity.Restaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;

public interface OrderCustomRepository {
    Slice<Order> restaurantOrderSearchByPeriod(Restaurant restaurant, LocalDate start, LocalDate end, Long lastId, Pageable pageable);
}
