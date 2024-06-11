package com.study.delivery.domain.restaurant.entity;

import com.study.delivery.global.entity.BaseEntity;
import com.study.delivery.global.exception.BusinessException;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

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

    public void verifyName(String name) {
        if (!this.name.equals(name)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "가게가 변경되었어요!");
        }
    }
}
