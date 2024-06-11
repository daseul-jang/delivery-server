package com.study.delivery.domain.menu.entity;

import com.study.delivery.domain.restaurant.entity.Restaurant;
import com.study.delivery.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menu_group")
public class MenuGroup extends BaseEntity {
    private String subject;

    @OneToMany(mappedBy = "menuGroup")
    @Builder.Default
    private List<MenuGroupAssignment> menus = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
