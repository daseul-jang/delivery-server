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
@Table(name = "menu_group_assignment")
public class MenuGroupAssignment extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_group_id")
    private MenuGroup menuGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public void addMenuGroup(MenuGroup menuGroup) {
        this.menuGroup = menuGroup;
        menuGroup.getMenus().add(this);
    }

    public void addMenu(Menu menu) {
        this.menu = menu;
        menu.getMenuGroups().add(this);
    }
}
