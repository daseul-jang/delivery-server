package com.study.delivery.domain.restaurant.menu.dao;

import com.study.delivery.domain.restaurant.menu.entity.MenuOptionAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuOptionAssignmentRepository extends JpaRepository<MenuOptionAssignment, Long> {
}
