package com.study.delivery.domain.order.order.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenuOption {
    private final Long optionGroupId;
    private final String optionGroupName;
    private final List<OptionItem> optionItems;

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OptionItem {
        private final Long optionId;
        private final String optionName;
        private final BigDecimal optionPrice;
    }
}
