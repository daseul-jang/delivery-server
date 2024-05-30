package com.study.delivery.domain.restaurant.restaurant.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryFee {
    private final BigDecimal minOrderAmount;
    private final BigDecimal maxOrderAmount;
    private final BigDecimal fee;
}
