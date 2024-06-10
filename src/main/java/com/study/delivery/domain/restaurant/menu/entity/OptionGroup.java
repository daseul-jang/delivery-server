package com.study.delivery.domain.restaurant.menu.entity;

import com.study.delivery.domain.order.order.vo.OrderMenuOption;
import com.study.delivery.domain.restaurant.restaurant.entity.Restaurant;
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
@Table(name = "option_group")
public class OptionGroup extends BaseEntity {
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "optionGroup")
    @Builder.Default
    private List<MenuOptionGroupAssignment> menus = new ArrayList<>();

    @OneToMany(mappedBy = "optionGroup")
    @Builder.Default
    private List<Option> options = new ArrayList<>();

    public OrderMenuOption createOrderOptionGroup(List<OrderMenuOption.OptionItem> orderOptionItems) {
        return OrderMenuOption.builder()
                .optionGroupId(this.getId())
                .optionGroupName(this.name)
                .optionItems(orderOptionItems)
                .build();
    }
}
