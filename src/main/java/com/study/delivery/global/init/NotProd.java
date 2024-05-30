package com.study.delivery.global.init;

import com.study.delivery.domain.restaurant.menu.dao.*;
import com.study.delivery.domain.restaurant.menu.entity.*;
import com.study.delivery.domain.restaurant.restaurant.dao.RestaurantRepository;
import com.study.delivery.domain.restaurant.restaurant.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

@Profile("!prod & !test")
@Component
@RequiredArgsConstructor
public class NotProd implements ApplicationRunner {
    private final RestaurantRepository restaurantRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final MenuRepository menuRepository;
    private final MenuGroupAssignmentRepository menuGroupAssignmentRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionRepository optionRepository;
    private final MenuOptionGroupAssignmentRepository menuOptionGroupAssignmentRepository;
    private final MenuOptionAssignmentRepository menuOptionAssignmentRepository;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!restaurantRepository.findAll().isEmpty()) {
            return;
        }

        initRestaurant();
        initMenuGroup();
        initMenu();
        initOptionGroup();
        initOption();
    }

    private void initRestaurant() {
        String[] names = {"BHC 광주점", "반올림피자 광주점", "롯데리아 광주점"};

        for (String name : names) {
            restaurantRepository.save(
                    Restaurant.builder()
                            .name(name)
                            .minOrderPrice(BigDecimal.valueOf(20000))
                            .deliveryPrice(BigDecimal.valueOf(2000))
                            .build()
            );
        }
    }

    private void initMenuGroup() {
        restaurantRepository.findById(1L).ifPresent(restaurant -> {
            createMenuGroup(restaurant, "추천 메뉴", "뿌링클", "레드킹");
        });

        restaurantRepository.findById(2L).ifPresent(restaurant -> {
            createMenuGroup(restaurant, "추천 메뉴", "신메뉴", "오리지널 피자");
        });

        restaurantRepository.findById(3L).ifPresent(restaurant -> {
            createMenuGroup(restaurant, "추천 메뉴", "세트 메뉴", "단품 메뉴");
        });
    }

    private void createMenuGroup(Restaurant restaurant, String... subjects) {
        for (String subject : subjects) {
            menuGroupRepository.save(
                    MenuGroup.builder()
                            .subject(subject)
                            .restaurant(restaurant)
                            .build());
        }
    }

    private void initMenu() {
        // bhc
        Restaurant bhc = restaurantRepository.findById(1L).get();

        MenuGroup BHC_추천메뉴_그룹 = menuGroupRepository.findById(1L).get();
        MenuGroup 뿌링클_그룹 = menuGroupRepository.findById(2L).get();
        MenuGroup 레드킹_그룹 = menuGroupRepository.findById(3L).get();

        Menu 뿌링클_콤보 = createMenu("뿌링클 콤보", bhc, BigDecimal.valueOf(15000));
        Menu 뿌링클_윙 = createMenu("뿌링클 윙", bhc, BigDecimal.valueOf(15000));
        Menu 맛초킹_콤보 = createMenu("맛초킹 콤보", bhc, BigDecimal.valueOf(15000));
        Menu 맛초킹_윙 = createMenu("맛초킹 윙", bhc, BigDecimal.valueOf(15000));
        Menu 레드킹_콤보 = createMenu("레드킹_콤보", bhc, BigDecimal.valueOf(15000));
        Menu 레드킹_윙 = createMenu("레드킹_윙", bhc, BigDecimal.valueOf(15000));

        menuGroupAssignmentSetting(BHC_추천메뉴_그룹, 뿌링클_콤보, 맛초킹_콤보, 레드킹_콤보);
        menuGroupAssignmentSetting(뿌링클_그룹, 뿌링클_콤보, 뿌링클_윙);
        menuGroupAssignmentSetting(레드킹_그룹, 레드킹_콤보, 레드킹_윙);

        // 반올림 피자
        Restaurant banolim = restaurantRepository.findById(2L).get();

        MenuGroup 반올림_추천메뉴_그룹 = menuGroupRepository.findById(4L).get();
        MenuGroup 신메뉴_그룹 = menuGroupRepository.findById(5L).get();
        MenuGroup 오리지널피자_그룹 = menuGroupRepository.findById(6L).get();

        Menu 반반_피자 = createMenu("반반 피자", banolim, BigDecimal.ZERO);
        Menu 불고기_피자 = createMenu("불고기 피자", banolim, BigDecimal.ZERO);
        Menu 더블바베큐_피자 = createMenu("더블바베큐 피자", banolim, BigDecimal.ZERO);
        Menu 레드올림고구마_피자 = createMenu("레드올림고구마 피자", banolim, BigDecimal.ZERO);

        menuGroupAssignmentSetting(반올림_추천메뉴_그룹, 반반_피자, 불고기_피자, 더블바베큐_피자);
        menuGroupAssignmentSetting(신메뉴_그룹, 레드올림고구마_피자);
        menuGroupAssignmentSetting(오리지널피자_그룹, 불고기_피자, 더블바베큐_피자);
    }

    private Menu createMenu(String menuName, Restaurant restaurant, BigDecimal price) {
        return menuRepository.save(
                Menu.builder()
                        .name(menuName)
                        .restaurant(restaurant)
                        .price(price)
                        .build()
        );
    }

    private void menuGroupAssignmentSetting(MenuGroup menuGroup, Menu... menus) {
        for (Menu menu : menus) {
            MenuGroupAssignment menuGroupAssignment = menuGroupAssignmentRepository.save(
                    MenuGroupAssignment.builder()
                            .menuGroup(menuGroup)
                            .menu(menu)
                            .build()
            );

            menuGroupAssignment.addMenuGroup(menuGroup);
            menuGroupAssignment.addMenu(menu);
        }
    }

    private void initOptionGroup() {
        // bhc
        createOptionGroup(1L, "사이드 메뉴 선택", "소스 선택");

        // 반올림
        createOptionGroup(2L, "사이즈", "도우 선택", "소스 선택");
    }

    private void createOptionGroup(Long restaurantId, String... subjects) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

        for (String subject : subjects) {
            optionGroupRepository.save(
                    OptionGroup.builder()
                            .subject(subject)
                            .restaurant(restaurant)
                            .build()
            );
        }

        List<OptionGroup> optionGroups = optionGroupRepository.findAllByRestaurant(restaurant);
        List<Menu> menus = menuRepository.findAllByRestaurant(restaurant);

        for (OptionGroup optionGroup : optionGroups) {
            for (Menu menu : menus) {
                MenuOptionGroupAssignment menuOptionGroupAssignment =
                        menuOptionGroupAssignmentRepository.save(
                                MenuOptionGroupAssignment.builder()
                                        .optionGroup(optionGroup)
                                        .menu(menu)
                                        .build()
                        );

                menuOptionGroupAssignment.addMenu(menu);
                menuOptionGroupAssignment.addOptionGroup(optionGroup);
            }
        }
    }

    private void initOption() {
        // bhc
        // 옵션 그룹 1: 사이드 메뉴 선택 / 2: 소스 선택
        Restaurant bhc = restaurantRepository.findById(1L).get();
        List<Menu> bhcMenus = menuRepository.findAllByRestaurant(bhc);

        createOption(bhc, "웨지감자", BigDecimal.valueOf(6000), 1L);
        createOption(bhc, "뿌링치즈볼", BigDecimal.valueOf(6500), 1L);
        createOption(bhc, "달콤바삭치즈볼", BigDecimal.valueOf(5500), 1L);
        createOption(bhc, "뿌링감자", BigDecimal.valueOf(5000), 1L);
        createOption(bhc, "뿌링핫도그", BigDecimal.valueOf(6000), 1L);

        String[] sauces = {"뿌링뿌링 소스 추가", "맵소사 소스 추가", "스윗하바네로 소스 추가"};

        for (String sauce : sauces) {
            createOption(bhc, sauce, BigDecimal.valueOf(2500), 3L);
        }

        List<Option> bhcOptions = optionRepository.findAllByRestaurant(bhc);

        for (Menu menu : bhcMenus) {
            for (Option option : bhcOptions) {
                createMenuOptionAssignment(menu, option);
            }
        }

        // 반올림 피자
        // 옵션 그룹 3: 사이즈 선택 / 4: 도우 선택 / 5: 소스 선택
        Restaurant banolim = restaurantRepository.findById(2L).get();

        createMenuOptionAssignment(
                menuRepository.findById(8L).get(),
                createOption(banolim, "레귤러", BigDecimal.valueOf(19900), 3L));

        createMenuOptionAssignment(
                menuRepository.findById(8L).get(),
                createOption(banolim, "라지", BigDecimal.valueOf(22900), 3L));

        createMenuOptionAssignment(
                menuRepository.findById(9L).get(),
                createOption(banolim, "레귤러", BigDecimal.valueOf(20900), 3L));

        createMenuOptionAssignment(
                menuRepository.findById(9L).get(),
                createOption(banolim, "라지", BigDecimal.valueOf(23900), 3L));
    }

    private Option createOption(Restaurant restaurant, String optionText, BigDecimal price, Long optionGroupId) {
        Option option = optionRepository.save(
                Option.builder()
                        .text(optionText)
                        .price(price)
                        .restaurant(restaurant)
                        .build()
        );

        option.addOptionGroup(
                optionGroupRepository.findById(optionGroupId).get()
        );

        return option;
    }

    private void createMenuOptionAssignment(Menu menu, Option option) {
        MenuOptionAssignment menuOptionAssignment =
                menuOptionAssignmentRepository.save(
                        MenuOptionAssignment.builder()
                                .menu(menu)
                                .option(option)
                                .build()
                );

        menuOptionAssignment.addMenu(menu);
        menuOptionAssignment.addOption(option);
    }
}
