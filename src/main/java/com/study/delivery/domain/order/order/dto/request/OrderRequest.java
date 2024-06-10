package com.study.delivery.domain.order.order.dto.request;

import com.study.delivery.domain.order.order.entity.Order;
import com.study.delivery.domain.order.order.entity.OrderMenu;
import com.study.delivery.domain.restaurant.restaurant.entity.Restaurant;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderRequest {
    private Long restaurantId;
    private String restaurantName;
    private String ordererName;
    private String ordererPhone;
    private String address;
    private String addressDetail;
    private String orderRequest;
    private String deliveryRequest;
    private BigDecimal orderPrice;
    private BigDecimal deliveryPrice;
    private BigDecimal totalPrice;
    private List<OrderMenuRequest> orderMenus;

    public static Order toEntity(OrderRequest request,
                                 Restaurant restaurant,
                                 List<OrderMenu> menus,
                                 String orderNo,
                                 BigDecimal orderPrice) {
        return Order.builder()
                .orderNo(orderNo)
                .ordererName(request.getOrdererName())
                .ordererPhone(request.getOrdererPhone())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .orderRequest(request.getOrderRequest())
                .deliveryRequest(request.getDeliveryRequest())
                .orderPrice(orderPrice)
                .deliverPrice(restaurant.getDeliveryPrice())
                .totalPrice(orderPrice.add(restaurant.getDeliveryPrice()))
                .orderMenus(menus)
                .restaurant(restaurant)
                .build();
    }
}
