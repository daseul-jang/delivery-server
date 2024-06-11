package com.study.delivery.domain.restaurant.dto.response;

import com.study.delivery.domain.restaurant.entity.Restaurant;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantResponse {
    private Long id;
    private String name;
    private String notice;
    private BigDecimal minOrderPrice;
    private BigDecimal deliveryPrice;

    public static RestaurantResponse of(Restaurant restaurant) {
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .notice(restaurant.getNotice())
                .minOrderPrice(restaurant.getMinOrderPrice())
                .deliveryPrice(restaurant.getDeliveryPrice())
                .build();
    }
}
