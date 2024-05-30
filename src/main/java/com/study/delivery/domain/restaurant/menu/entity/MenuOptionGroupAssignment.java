package com.study.delivery.domain.restaurant.menu.entity;

import com.study.delivery.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menu_option_group_assignment")
public class MenuOptionGroupAssignment extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private OptionGroup optionGroup;

    public void addMenu(Menu menu) {
        this.menu = menu;
        menu.getOptionGroups().add(this);
    }

    public void addOptionGroup(OptionGroup optionGroup) {
        this.optionGroup = optionGroup;
        optionGroup.getMenus().add(this);
    }
}
