package com.study.delivery.domain.order.order.dao.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.delivery.domain.order.order.entity.Order;
import com.study.delivery.domain.restaurant.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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

    /**
     * no-offset 기반 페이지네이션 (커서 기반 페이지네이션)
     *
     * @param targetRestaurant 주문 목록을 조회하는 가게
     * @param start            조회 시작 일자
     * @param end              조회 종료 일자
     * @param lastId           주문의 마지막 id
     * @param pageable         커서 정보를 담은 객체
     * @return 주문 목록
     */
    @Override
    public Slice<Order> restaurantOrderSearchByPeriod(Restaurant targetRestaurant,
                                                      LocalDate start,
                                                      LocalDate end,
                                                      Long lastId,
                                                      Pageable pageable) {
        BooleanBuilder cond = new BooleanBuilder();

        cond.and(order.restaurant.eq(targetRestaurant))
                .and(ltCursorId(lastId));

        if (start != null && end != null) {
            cond.and(order.createdAt.between(start.atStartOfDay(), end.atTime(23, 59, 59)));
        } else if (start != null) {
            cond.and(order.createdAt.between(start.atStartOfDay(), start.atTime(23, 59, 59)));
        } else if (end != null) {
            cond.and(order.createdAt.between(end.atStartOfDay(), end.atTime(23, 59, 59)));
        }

        List<Order> orders = jpaQueryFactory
                .selectFrom(order)
                .leftJoin(order.restaurant, restaurant)
                .leftJoin(order.orderMenus, orderMenu)
                .where(cond)
                .orderBy(order.id.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return checkLastPage(pageable, orders);
    }

    private BooleanExpression ltCursorId(Long lastId) {
        return lastId == null ? null : order.id.lt(lastId);
    }

    private Slice<Order> checkLastPage(Pageable pageable, List<Order> orders) {
        // 다음 데이터가 있는지 여부. true: 있음, false: 없음
        boolean hasNext = false;

        if (orders.size() > pageable.getPageSize()) {
            hasNext = true;
            orders.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(orders, pageable, hasNext);
    }
}
