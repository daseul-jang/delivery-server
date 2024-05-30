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
@Table(name = "`option`")
public class Option extends BaseEntity {
    private String text;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_option_group_id")
    private OptionGroup optionGroup;

    @OneToMany(mappedBy = "option")
    @Builder.Default
    private List<MenuOptionAssignment> menus = new ArrayList<>();

    public void addOptionGroup(OptionGroup optionGroup) {
        this.optionGroup = optionGroup;
        optionGroup.getOptions().add(this);
    }
}
