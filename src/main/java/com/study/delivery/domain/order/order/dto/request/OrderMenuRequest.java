package com.study.delivery.domain.order.order.dto.request;

import com.study.delivery.domain.order.order.vo.OrderMenu;
import com.study.delivery.domain.order.order.vo.OrderMenuOption;
import com.study.delivery.domain.restaurant.menu.entity.Menu;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenuRequest {
    private Long menuId;
    private String name;
    private Long quantity;
    private BigDecimal price;
    private List<OrderMenuOption> options;

    public static OrderMenu to(Menu menu,
                               BigDecimal optionTotalPrice,
                               List<OrderMenuOption> options,
                               Long quantity) {
        return OrderMenu.builder()
                .menuId(menu.getId())
                .menuName(menu.getName())
                .quantity(quantity)
                .price(menu.getPrice())
                .totalPrice(menu.getPrice().add(optionTotalPrice))
                .options(options)
                .build();
    }
}
