package com.study.delivery.domain.restaurant.menu.dao;

import com.study.delivery.domain.restaurant.menu.entity.Option;
import com.study.delivery.domain.restaurant.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findAllByRestaurant(Restaurant restaurant);

    Optional<Option> findByIdAndTextAndRestaurant(Long id, String text, Restaurant restaurant);
}
