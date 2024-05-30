package com.study.delivery.domain.order.order.dto.response;

import com.study.delivery.domain.order.order.entity.Order;
import com.study.delivery.domain.order.order.vo.OrderMenu;
import com.study.delivery.domain.restaurant.restaurant.dto.response.RestaurantResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderResponse {
    private Long id;
    private RestaurantResponse restaurant;
    private LocalDateTime createdAt;
    private String orderNo;
    private String ordererName;
    private String ordererPhone;
    private String address;
    private String addressDetail;
    private String orderRequest;
    private String deliveryRequest;
    private BigDecimal orderPrice;
    private BigDecimal deliveryPrice;
    private BigDecimal totalPrice;
    private List<OrderMenu> orderMenus;

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .restaurant(RestaurantResponse.of(order.getRestaurant()))
                .createdAt(order.getCreatedAt())
                .orderNo(order.getOrderNo())
                .ordererName(order.getOrdererName())
                .ordererPhone(order.getOrdererPhone())
                .address(order.getAddress())
                .addressDetail(order.getAddressDetail())
                .orderRequest(order.getOrderRequest())
                .deliveryRequest(order.getDeliveryRequest())
                .orderPrice(order.getOrderPrice())
                .deliveryPrice(order.getDeliverPrice())
                .totalPrice(order.getTotalPrice())
                .orderMenus(order.getOrderMenus())
                .build();
    }
}
