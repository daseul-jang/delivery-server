package com.study.delivery.domain.order.order.entity;

import com.study.delivery.domain.order.order.dto.request.OrderRequest;
import com.study.delivery.domain.restaurant.restaurant.entity.Restaurant;
import com.study.delivery.global.entity.BaseEntity;
import com.study.delivery.global.exception.BusinessException;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`order`")
public class Order extends BaseEntity {
    private String orderNo;

    private String ordererName;

    private String ordererPhone;

    private String address;

    private String addressDetail;

    private String orderRequest;

    private String deliveryRequest;

    private BigDecimal orderPrice;

    private BigDecimal deliverPrice;

    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order")
    @Builder.Default
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public static Order create(OrderRequest request, Restaurant restaurant) {
        return Order.builder()
                .orderNo(generateOrderNo(request.getOrdererPhone()))
                .ordererName(request.getOrdererName())
                .ordererPhone(request.getOrdererPhone())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .orderRequest(request.getOrderRequest())
                .deliveryRequest(request.getDeliveryRequest())
                .orderPrice(BigDecimal.ZERO)
                .deliverPrice(restaurant.getDeliveryPrice())
                .totalPrice(BigDecimal.ZERO)
                .restaurant(restaurant)
                .build();
    }

    private static String generateOrderNo(String ordererPhone) {
        int index = ordererPhone.length() - 4;
        String endNumber = ordererPhone.substring(index);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        // 현재 시간을 기반으로 한 타임스탬프 생성
        String timestamp = dateFormat.format(new Date());

        // 타임스탬프와 랜덤 숫자 결합
        return timestamp + endNumber;
    }

    public void addOrderMenus(List<OrderMenu> orderMenus) {
        this.orderMenus = orderMenus;
    }

    public void addOrderPrice(BigDecimal finalMenuPrice) {
        this.orderPrice = orderPrice.add(finalMenuPrice);
    }

    public void verifyPrice() {
        if (this.orderPrice.compareTo(this.restaurant.getMinOrderPrice()) < 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "최소 주문 금액을 만족하지 못했습니다.");
        }

        this.totalPrice = this.orderPrice.add(this.deliverPrice);
    }
}

