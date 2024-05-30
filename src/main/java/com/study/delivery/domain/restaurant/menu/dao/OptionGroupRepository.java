package com.study.delivery.domain.restaurant.menu.dao;

import com.study.delivery.domain.restaurant.menu.entity.OptionGroup;
import com.study.delivery.domain.restaurant.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long> {
    List<OptionGroup> findAllByRestaurant(Restaurant restaurant);

    Optional<OptionGroup> findByIdAndSubjectAndRestaurant(Long id, String subject, Restaurant restaurant);
}
