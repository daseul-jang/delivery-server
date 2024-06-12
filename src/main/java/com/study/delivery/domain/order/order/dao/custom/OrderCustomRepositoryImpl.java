package com.study.delivery.domain.order.order.dao.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.delivery.domain.order.order.entity.Order;
import com.study.delivery.domain.restaurant.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.study.delivery.domain.order.order.entity.QOrder.order;
import static com.study.delivery.domain.order.order.entity.QOrderMenu.orderMenu;
import static com.study.delivery.domain.restaurant.entity.QRestaurant.restaurant;

@Repository
@RequiredArgsConstructor
public class OrderCustomRepositoryImpl implements OrderCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Order> restaurantOrderSearchByPeriod(Restaurant targetRestaurant,
                                                     LocalDate start,
                                                     LocalDate end,
                                                     Pageable pageable) {
        BooleanBuilder cond = new BooleanBuilder();

        cond.and(order.restaurant.eq(targetRestaurant));

        if (start != null && end != null) {
            cond.and(order.createdAt.between(start.atStartOfDay(), end.atTime(23, 59, 59)));
        } else if (start != null) {
            cond.and(order.createdAt.between(start.atStartOfDay(), start.atTime(23, 59, 59)));
        } else if (end != null) {
            cond.and(order.createdAt.between(end.atStartOfDay(), end.atTime(23, 59, 59)));
        }

        List<Order> orders = jpaQueryFactory
                .selectFrom(order)
                .leftJoin(order.restaurant, restaurant).fetchJoin()
                .leftJoin(order.orderMenus, orderMenu).fetchJoin()
                .where(cond)
                .orderBy(order.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> total = jpaQueryFactory
                .select(order.count())
                .from(order)
                .where(cond);

        return PageableExecutionUtils.getPage(orders, pageable, total::fetchOne);
    }
}
