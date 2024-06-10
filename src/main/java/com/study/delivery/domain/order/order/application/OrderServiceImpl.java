package com.study.delivery.domain.order.order.application;

import com.study.delivery.domain.order.order.dao.OrderMenuRepository;
import com.study.delivery.domain.order.order.dao.OrderRepository;
import com.study.delivery.domain.order.order.dto.request.OrderMenuRequest;
import com.study.delivery.domain.order.order.dto.request.OrderRequest;
import com.study.delivery.domain.order.order.dto.response.OrderMenuResponse;
import com.study.delivery.domain.order.order.dto.response.OrderResponse;
import com.study.delivery.domain.order.order.entity.Order;
import com.study.delivery.domain.order.order.entity.OrderMenu;
import com.study.delivery.domain.order.order.vo.OrderMenuOption;
import com.study.delivery.domain.restaurant.menu.dao.MenuRepository;
import com.study.delivery.domain.restaurant.menu.dao.OptionGroupRepository;
import com.study.delivery.domain.restaurant.menu.dao.OptionRepository;
import com.study.delivery.domain.restaurant.menu.entity.Menu;
import com.study.delivery.domain.restaurant.menu.entity.Option;
import com.study.delivery.domain.restaurant.menu.entity.OptionGroup;
import com.study.delivery.domain.restaurant.restaurant.dao.RestaurantRepository;
import com.study.delivery.domain.restaurant.restaurant.entity.Restaurant;
import com.study.delivery.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionRepository optionRepository;

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        validateUserInfo(orderRequest);

        Restaurant restaurant = restaurantRepository.findByName(orderRequest.getRestaurantName())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "존재하지 않는 가게입니다."));

        restaurant.verifyName(orderRequest.getRestaurantName());

        Order order = Order.create(orderRequest, restaurant);
        orderRepository.save(order);

        List<OrderMenu> orderMenus = new ArrayList<>();

        for (OrderMenuRequest requestMenu : orderRequest.getOrderMenus()) {
            Menu originalMenu = menuRepository.findByName(requestMenu.getName())
                    .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "존재하지 않는 메뉴입니다."));

            originalMenu.verifyRestaurant(restaurant);

            OrderMenu orderMenu = originalMenu.createOrderMenu(order, requestMenu.getQuantity());
            orderMenu.addOrder(order);

            List<OrderMenuOption> orderMenuOptions = new ArrayList<>();

            BigDecimal totalOptionPrice = BigDecimal.ZERO;

            for (OrderMenuOption requestOption : requestMenu.getOptions()) {
                List<OrderMenuOption.OptionItem> orderOptionItems = new ArrayList<>();

                OptionGroup originalOptionGroup = optionGroupRepository.findById(requestOption.getOptionGroupId())
                        .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "존재하지 않는 옵션 그룹입니다."));

                for (OrderMenuOption.OptionItem requestOptionItem : requestOption.getOptionItems()) {
                    Option originalOption = optionRepository.findById(requestOptionItem.getOptionId())
                            .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "존재하지 않는 옵션입니다."));

                    originalOption.verifyName(requestOptionItem.getOptionName());

                    totalOptionPrice = totalOptionPrice.add(
                            originalOption.verifyPrice(requestOptionItem.getOptionPrice()));

                    orderOptionItems.add(
                            originalOption.createOrderOption()
                    );
                }

                orderMenuOptions.add(
                        originalOptionGroup.createOrderOptionGroup(orderOptionItems)
                );
            }

            BigDecimal finalMenuPrice = originalMenu.verifyPrice(totalOptionPrice, requestMenu.getQuantity());
            order.addOrderPrice(finalMenuPrice);

            orderMenus.add(
                    orderMenuRepository.save(
                            orderMenu.toBuilder()
                                    .totalPrice(finalMenuPrice)
                                    .options(orderMenuOptions)
                                    .build()
                    )

            );
        }

        order.addOrderMenus(orderMenus);
        order.verifyPrice();

        return OrderResponse.of(order,
                orderMenus.stream().map(OrderMenuResponse::of).collect(Collectors.toList()));
    }

    private void validateUserInfo(OrderRequest orderRequest) {
        if (orderRequest.getOrdererName().isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "이름은 비워둘 수 없습니다.");
        }

        if (orderRequest.getOrdererPhone().isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "전화번호는 비워둘 수 없습니다.");
        }

        if (orderRequest.getAddress().isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "주소는 비워둘 수 없습니다.");
        }

        if (orderRequest.getAddressDetail().isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "상세 주소는 비워둘 수 없습니다.");
        }
    }
}
