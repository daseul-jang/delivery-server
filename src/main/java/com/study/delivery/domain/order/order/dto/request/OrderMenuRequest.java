package com.study.delivery.domain.order.order.dto.request;

import com.study.delivery.domain.order.order.vo.OrderMenuOption;
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
}
