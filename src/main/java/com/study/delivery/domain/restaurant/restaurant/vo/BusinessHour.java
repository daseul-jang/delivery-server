package com.study.delivery.domain.restaurant.restaurant.vo;

import lombok.*;

import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessHour {
    private final LocalTime openTime;
    private final LocalTime closeTime;
}
