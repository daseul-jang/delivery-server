package com.study.delivery.domain.order.order.dao;

import com.study.delivery.domain.order.order.dao.custom.OrderCustomRepository;
import com.study.delivery.domain.order.order.entity.Order;
import com.study.delivery.domain.restaurant.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustomRepository {
    List<Order> findAllByRestaurant(Restaurant restaurant);
}