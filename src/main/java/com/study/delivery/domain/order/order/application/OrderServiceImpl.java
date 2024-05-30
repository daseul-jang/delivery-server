package com.study.delivery.domain.order.order.application;

import com.study.delivery.domain.order.order.dao.OrderRepository;
import com.study.delivery.domain.order.order.dto.request.OrderMenuRequest;
import com.study.delivery.domain.order.order.dto.request.OrderRequest;
import com.study.delivery.domain.order.order.dto.response.OrderResponse;
import com.study.delivery.domain.order.order.entity.Order;
import com.study.delivery.domain.order.order.vo.OrderMenu;
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
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionRepository optionRepository;

    @Override
    public List<OrderResponse> getOrders(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "존재하지 않는 가게"));
        return orderRepository.findAllByRestaurant(restaurant).stream()
                .map(OrderResponse::of).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        validateUserInfo(orderRequest);

        Restaurant restaurant = restaurantRepository.findByIdAndName(
                        orderRequest.getRestaurantId(), orderRequest.getRestaurantName())
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "존재하지 않는 가게"));

        BigDecimal orderPrice = BigDecimal.ZERO;

        List<OrderMenu> menus = new ArrayList<>();

        // 주문한 메뉴가 DB와 일치하는지 검증
        for (OrderMenuRequest orderMenuRequest : orderRequest.getOrderMenus()) {
            Menu menu = menuRepository.findByIdAndNameAndRestaurant(orderMenuRequest.getMenuId(), orderMenuRequest.getName(), restaurant)
                    .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "존재하지 않는 메뉴"));

            if (!orderMenuRequest.getPrice().equals(menu.getPrice())) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "메뉴 가격이 일치하지 않음");
            }

            BigDecimal optionTotalPrice = BigDecimal.ZERO;

            List<OrderMenuOption> orderMenuOptions = new ArrayList<>();

            if (!orderMenuRequest.getOptions().isEmpty()) {
                // 메뉴 옵션이 DB와 일치하는지 검증
                for (OrderMenuOption orderMenuOption : orderMenuRequest.getOptions()) {
                    OptionGroup optionGroup = optionGroupRepository.findByIdAndSubjectAndRestaurant(
                                    orderMenuOption.getOptionGroupId(),
                                    orderMenuOption.getOptionGroupSubject(),
                                    restaurant)
                            .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "존재하지 않는 옵션 그룹"));

                    List<OrderMenuOption.OptionItem> optionItems = new ArrayList<>();

                    for (OrderMenuOption.OptionItem optionItem : orderMenuOption.getOptionItems()) {
                        Option option = optionRepository.findByIdAndTextAndRestaurant(optionItem.getOptionId(), optionItem.getOptionText(), restaurant)
                                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "존재하지 않는 옵션"));

                        if (!optionItem.getOptionPrice().equals(option.getPrice())) {
                            throw new BusinessException(HttpStatus.BAD_REQUEST, "옵션 가격이 일치하지 않음");
                        }

                        optionTotalPrice = optionTotalPrice.add(option.getPrice());

                        optionItems.add(
                                OrderMenuOption.OptionItem.builder()
                                        .optionId(option.getId())
                                        .optionText(option.getText())
                                        .optionPrice(option.getPrice())
                                        .build()
                        );
                    }

                    orderMenuOptions.add(
                            OrderMenuOption.builder()
                                    .optionGroupId(optionGroup.getId())
                                    .optionGroupSubject(optionGroup.getSubject())
                                    .optionItems(optionItems)
                                    .build()
                    );
                }
            }

            OrderMenu orderMenu = OrderMenuRequest.to(menu, optionTotalPrice, orderMenuOptions, orderMenuRequest.getQuantity());
            menus.add(orderMenu);

            orderPrice = orderPrice.add(orderMenu.getTotalPrice()).multiply(BigDecimal.valueOf(orderMenuRequest.getQuantity()));
        }

        int comparisonResult = orderPrice.compareTo(restaurant.getMinOrderPrice());

        if (comparisonResult < 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "최소 주문 금액을 만족하지 못했습니다.");
        }

        Order order = OrderRequest.toEntity(
                orderRequest,
                restaurant,
                menus,
                createOrderNo(orderRequest.getOrdererPhone()),
                orderPrice);

        return OrderResponse.of(orderRepository.save(order));
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

    private String createOrderNo(String ordererPhone) {
        // 주문자 핸드폰 번호 뒷 네자리 추출
        int index = ordererPhone.length() - 4;
        String number = ordererPhone.substring(index);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        // 현재 시간을 기반으로 한 타임스탬프 생성
        String timestamp = dateFormat.format(new Date());

        // 타임스탬프와 랜덤 숫자 결합
        return timestamp + number;
    }
}
