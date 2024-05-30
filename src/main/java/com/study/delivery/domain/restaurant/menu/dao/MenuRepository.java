package com.study.delivery.domain.restaurant.menu.dao;

import com.study.delivery.domain.restaurant.menu.entity.Menu;
import com.study.delivery.domain.restaurant.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByRestaurant(Restaurant restaurant);

    Optional<Menu> findByIdAndNameAndRestaurant(Long id, String name, Restaurant restaurant);
}
