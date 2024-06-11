package com.study.delivery.domain.restaurant.dao;

import com.study.delivery.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByIdAndName(Long id, String name);

    Optional<Restaurant> findByName(String name);
}
