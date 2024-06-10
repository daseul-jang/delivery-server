package com.study.delivery.domain.order.order.dto.response;

import com.study.delivery.domain.order.order.entity.OrderMenu;
import com.study.delivery.domain.order.order.vo.OrderMenuOption;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenuResponse {
    private Long id;
    private String name;
    private Long quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private List<OrderMenuOption> options = new ArrayList<>();

    public static OrderMenuResponse of(OrderMenu orderMenu) {
        return OrderMenuResponse.builder()
                .id(orderMenu.getId())
                .name(orderMenu.getName())
                .quantity(orderMenu.getQuantity())
                .price(orderMenu.getPrice())
                .totalPrice(orderMenu.getTotalPrice())
                .options(orderMenu.getOptions())
                .build();
    }
}
