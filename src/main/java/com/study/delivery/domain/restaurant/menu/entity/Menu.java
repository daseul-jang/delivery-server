package com.study.delivery.domain.restaurant.menu.entity;

import com.study.delivery.domain.restaurant.restaurant.entity.Restaurant;
import com.study.delivery.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menu")
public class Menu extends BaseEntity {
    private String name;

    private BigDecimal price;

    @OneToMany(mappedBy = "menu")
    @Builder.Default
    private List<MenuGroupAssignment> menuGroups = new ArrayList<>();

    @OneToMany(mappedBy = "menu")
    @Builder.Default
    private List<MenuOptionGroupAssignment> optionGroups = new ArrayList<>();

    @OneToMany(mappedBy = "menu")
    @Builder.Default
    private List<MenuOptionAssignment> options = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
