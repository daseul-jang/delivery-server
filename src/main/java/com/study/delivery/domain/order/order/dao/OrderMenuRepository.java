package com.study.delivery.domain.order.order.dao;

import com.study.delivery.domain.order.order.entity.Order;
import com.study.delivery.domain.order.order.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
    List<OrderMenu> findAllByOrder(Order order);
}
