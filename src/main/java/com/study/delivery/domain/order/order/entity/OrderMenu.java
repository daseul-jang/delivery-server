package com.study.delivery.domain.order.order.entity;

import com.study.delivery.domain.order.order.vo.OrderMenuOption;
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
@Table(name = "order_menu")
public class OrderMenu extends BaseEntity {
    private String name;

    private Long quantity;

    private BigDecimal price;

    private BigDecimal totalPrice;

    @Type(JsonType.class)
    @Column(columnDefinition = "JSON")
    @Builder.Default
    private List<OrderMenuOption> options = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public void addOrder(Order order) {
        this.order = order;
        order.getOrderMenus().add(this);
    }
}
