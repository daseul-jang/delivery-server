package com.study.delivery.domain.restaurant.restaurant.entity;

import com.study.delivery.domain.restaurant.restaurant.vo.BusinessHour;
import com.study.delivery.domain.restaurant.restaurant.vo.DeliveryFee;
import com.study.delivery.global.entity.BaseEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "restaurant")
public class Restaurant extends BaseEntity {
    private String name;

    private String notice;

    private BigDecimal minOrderPrice;

    private BigDecimal deliveryPrice;
}
