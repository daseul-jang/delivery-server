package com.study.delivery.domain.restaurant.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessHour {
    private final LocalTime openTime;
    private final LocalTime closeTime;
}
