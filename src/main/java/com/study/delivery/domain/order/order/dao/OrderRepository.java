package com.study.delivery.domain.order.order.dao;

import com.study.delivery.domain.order.order.dao.custom.OrderCustomRepository;
import com.study.delivery.domain.order.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustomRepository {
}
