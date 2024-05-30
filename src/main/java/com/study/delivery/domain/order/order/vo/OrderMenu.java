package com.study.delivery.domain.order.order.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenu {
    private final Long menuId;
    private final String menuName;
    private final Long quantity;
    private final BigDecimal price;
    private final BigDecimal totalPrice;
    private final List<OrderMenuOption> options;
}
