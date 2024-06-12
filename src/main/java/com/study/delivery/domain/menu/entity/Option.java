package com.study.delivery.domain.menu.entity;

import com.study.delivery.domain.order.order.vo.OrderMenuOption;
import com.study.delivery.domain.restaurant.entity.Restaurant;
import com.study.delivery.global.entity.BaseEntity;
import com.study.delivery.global.exception.BusinessException;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`option`")
public class Option extends BaseEntity {
    private String name;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_option_group_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private OptionGroup optionGroup;

    @OneToMany(mappedBy = "option")
    @Builder.Default
    private List<MenuOptionAssignment> menus = new ArrayList<>();

    public void addOptionGroup(OptionGroup optionGroup) {
        this.optionGroup = optionGroup;
        optionGroup.getOptions().add(this);
    }

    public OrderMenuOption.OptionItem createOrderOption() {
        return OrderMenuOption.OptionItem.builder()
                .optionId(this.getId())
                .optionName(this.name)
                .optionPrice(this.price)
                .build();
    }

    public void verifyName(String requestName) {
        if (!this.name.equals(requestName)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "해당 옵션 항목이 변경되었어요!");
        }
    }

    public BigDecimal verifyPrice(BigDecimal requestPrice) {
        if (!this.price.equals(requestPrice)) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "옵션 가격이 변경되었어요!");
        }

        return this.price;
    }
}
