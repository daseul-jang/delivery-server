package com.study.delivery.domain.restaurant.menu.dao;

import com.study.delivery.domain.restaurant.menu.entity.MenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuGroupRepository extends JpaRepository<MenuGroup, Long> {
}
