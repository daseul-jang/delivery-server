package com.study.delivery.domain.order.order.dao;

import com.study.delivery.domain.order.order.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
}
