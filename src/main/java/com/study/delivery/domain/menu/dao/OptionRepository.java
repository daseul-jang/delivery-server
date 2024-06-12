package com.study.delivery.domain.menu.dao;

import com.study.delivery.domain.menu.entity.Option;
import com.study.delivery.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findAllByRestaurant(Restaurant restaurant);

    Optional<Option> findByName(String name);
}
