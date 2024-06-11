package com.study.delivery.domain.menu.entity;

import com.study.delivery.domain.order.order.entity.Order;
import com.study.delivery.domain.order.order.entity.OrderMenu;
import com.study.delivery.domain.restaurant.entity.Restaurant;
import com.study.delivery.global.entity.BaseEntity;
import com.study.delivery.global.exception.BusinessException;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

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

    public OrderMenu createOrderMenu(Order order, Long quantity) {
        return OrderMenu.builder()
                .name(this.name)
                .quantity(quantity)
                .price(this.price)
                .order(order)
                .build();
    }

    public BigDecimal verifyPrice(BigDecimal totalOptionPrice, Long quantity) {
        return this.price.add(totalOptionPrice).multiply(BigDecimal.valueOf(quantity));
    }

    public void verifyRestaurant(Restaurant restaurant) {
        if (!this.restaurant.equals(restaurant)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "해당 가게에 존재하지 않는 메뉴입니다.");
        }
    }
}
