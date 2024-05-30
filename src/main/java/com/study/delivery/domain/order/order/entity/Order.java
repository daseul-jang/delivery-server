package com.study.delivery.domain.order.order.entity;

import com.study.delivery.domain.order.order.vo.OrderMenu;
import com.study.delivery.domain.restaurant.restaurant.entity.Restaurant;
import com.study.delivery.global.entity.BaseEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Type(JsonType.class)
    @Column(columnDefinition = "JSON")
    @Builder.Default
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

}

