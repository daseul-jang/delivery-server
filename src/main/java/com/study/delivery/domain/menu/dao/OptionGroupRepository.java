package com.study.delivery.domain.menu.dao;

import com.study.delivery.domain.menu.entity.OptionGroup;
import com.study.delivery.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long> {
    List<OptionGroup> findAllByRestaurant(Restaurant restaurant);

    Optional<OptionGroup> findByName(String name);
}
